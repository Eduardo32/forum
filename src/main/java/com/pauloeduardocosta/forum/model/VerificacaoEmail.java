package com.pauloeduardocosta.forum.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document("varificacaoemail")
public class VerificacaoEmail {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String uuid = UUID.randomUUID().toString();

    @NonNull
    @Field("usuario_id")
    private Long usuarioId;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private Boolean verificado = false;
}
