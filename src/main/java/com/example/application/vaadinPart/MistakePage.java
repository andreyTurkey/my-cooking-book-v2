package com.example.application.vaadinPart;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "/mistake")
@PageTitle("Public View")
@AnonymousAllowed
public class MistakePage extends VerticalLayout implements HasUrlParameter<String> {

    public MistakePage() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String o) {
        H4 mistake = new H4(o);
        add(mistake);
    }
}
