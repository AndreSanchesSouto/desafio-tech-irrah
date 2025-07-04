package com.irrah.back_end.enums;

public enum DocumentType {
    CNPJ("cnpj"),
    CPF("cpf");

    private String documentType;

    DocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return this.documentType;
    }
}
