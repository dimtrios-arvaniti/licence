package com.example.dim.licence.utils.uicustoms;

public class LoginPanel {

    private int cardId;
    private int resCard;
    private int resHeader;
    private int resHeaderImage;
    private int resContent;
    private boolean cardDisplayed;

    public LoginPanel() {
    }

    public LoginPanel(int cardId, int resCard, int resHeader, int resHeaderImage, int resContent, boolean cardDisplayed) {
        this.cardId = cardId;
        this.resCard = resCard;
        this.resHeader = resHeader;
        this.resHeaderImage = resHeaderImage;
        this.resContent = resContent;
        this.cardDisplayed = cardDisplayed;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getResCard() {
        return resCard;
    }

    public void setResCard(int resCard) {
        this.resCard = resCard;
    }

    public int getResHeader() {
        return resHeader;
    }

    public void setResHeader(int resHeader) {
        this.resHeader = resHeader;
    }

    public int getResHeaderImage() {
        return resHeaderImage;
    }

    public void setResHeaderImage(int resHeaderImage) {
        this.resHeaderImage = resHeaderImage;
    }

    public int getResContent() {
        return resContent;
    }

    public void setResContent(int resContent) {
        this.resContent = resContent;
    }

    public boolean isCardDisplayed() {
        return cardDisplayed;
    }

    public void setCardDisplayed(boolean cardDisplayed) {
        this.cardDisplayed = cardDisplayed;
    }
}
