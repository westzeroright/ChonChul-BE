package com.chonchul.qr;

import com.chonchul.auth.application.token.TokenProvider;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/sse")
@Tag(name = "큐알 API", description = "큐알 생성 관련 API")
public class QrSseController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final Map<SseEmitter, Long> emitterLectureMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final TokenProvider tokenProvider;

    public QrSseController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        scheduler.scheduleAtFixedRate(() -> {
            try {
                sendQrCodeUpdates();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Operation(summary = "큐알 생성 및 갱신", description = "큐알을 10초마다 생성합니다.")
    @GetMapping(value = "/connect/{lectureId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable Long lectureId) {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        emitterLectureMap.put(emitter, lectureId);

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            emitterLectureMap.remove(emitter);
        });
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            emitterLectureMap.remove(emitter);
        });

        return emitter;
    }

    private void sendQrCodeUpdates() throws WriterException {
        for (SseEmitter emitter : emitters) {
            Long lectureId = emitterLectureMap.get(emitter);
            if (lectureId != null) {
                byte[] qrCodeData = createQr(lectureId);
                String base64EncodedQr = Base64.getEncoder().encodeToString(qrCodeData);
                try {
                    emitter.send(SseEmitter.event()
                            .data(base64EncodedQr));
                } catch (Exception e) {
                    emitters.remove(emitter);
                    emitterLectureMap.remove(emitter);
                }
            }
        }
    }

    private byte[] createQr(Long lectureId) throws WriterException {
        int width = 300;
        int height = 300;
        String data = tokenProvider.createQrToken(lectureId);

        BitMatrix encode = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(encode, "PNG", out);
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Operation(summary = "큐알 생성 종료", description = "큐알 생성을 종료합니다.")
    @PostMapping("/cancel")
    public void cancel() {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .data("종료! 큐알 닫아주세요~"));
                emitter.complete();
            } catch (Exception e) {
                emitters.remove(emitter);
                emitterLectureMap.remove(emitter);
            }
        }
    }
}
