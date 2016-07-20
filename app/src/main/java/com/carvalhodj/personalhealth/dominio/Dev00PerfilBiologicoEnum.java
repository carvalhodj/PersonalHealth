package com.carvalhodj.personalhealth.dominio;

public class Dev00PerfilBiologicoEnum {

    public enum Caracteristica {
        X("CTGGTGGT"), Y("CACAGCCT"), Z("ATCTTCAA");
        private String sequencia;

        Caracteristica(String sequencia) {
            this.sequencia = sequencia;
        }

        public String getSequencia() {
            return sequencia;
        }

        public static Caracteristica getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }
}
