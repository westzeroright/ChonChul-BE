package com.chonchul.common;

import jakarta.persistence.PreRemove;

public class SoftDeleteListener {
    @PreRemove
    private void preRemove(BaseEntity entity) {
        entity.delete();
    }
}
