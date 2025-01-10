package app.vue;

import app.classes.Sujet;

public interface Observateur {
    public void actualiser(Sujet s);
}