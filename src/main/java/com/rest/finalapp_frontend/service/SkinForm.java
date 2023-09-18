package com.rest.finalapp_frontend.service;

import com.rest.finalapp_frontend.domain.SkinDto;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;

public class SkinForm extends FormLayout {

    private TextField skinName = new TextField("Skin Name");
    private TextField skinPriceUSD = new TextField("Skin Price USD");
    private TextField skinPricePLN = new TextField("Skin Price PLN");
    private Image skinImage = new Image();
    private BackendService backendService = BackendService.getInstance();
    private SkinDto skin;

    public SkinForm() {
        this.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));
        Span title = new Span("SKIN OF THE DAY");
        title.getElement().getStyle().set("font-size", "20px");
        skinName.setReadOnly(true);
        skinPriceUSD.setReadOnly(true);
        skinPricePLN.setReadOnly(true);

        skin = backendService.getRandomSkin();
        skinName.setValue(skin.getName());
        skinPriceUSD.setValue(String.format("%.2f", skin.getPriceUSD()));
        skinPricePLN.setValue(String.format("%.2f", skin.getPricePLN()));
        skinImage.setSrc(skin.getImageSrc());

        add(title, skinName, skinPriceUSD, skinPricePLN, skinImage);
    }
}
