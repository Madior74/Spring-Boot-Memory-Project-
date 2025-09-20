package com.example.schooladmin.etudiant.admission;


public class DossierActionRequest {

    private Long dossierId;
    private Long filiereAccepteeId;   // optionnel — si admin change
    private Long niveauAccepteId;     // optionnel — si admin change
    private String commentaireVoeu;   // optionnel
    private String status;    // obligatoire : ACCEPTE ou REFUSE

    // Getters / Setters
    public Long getDossierId() { return dossierId; }
    public void setDossierId(Long dossierId) { this.dossierId = dossierId; }

    public Long getFiliereAccepteeId() { return filiereAccepteeId; }
    public void setFiliereAccepteeId(Long filiereAccepteeId) { this.filiereAccepteeId = filiereAccepteeId; }

    public Long getNiveauAccepteId() { return niveauAccepteId; }
    public void setNiveauAccepteId(Long niveauAccepteId) { this.niveauAccepteId = niveauAccepteId; }

    public String getCommentaireVoeu() { return commentaireVoeu; }
    public void setCommentaireVoeu(String commentaireVoeu) { this.commentaireVoeu = commentaireVoeu; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}