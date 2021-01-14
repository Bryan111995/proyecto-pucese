/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Habitacion;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;

public class AgenteCardio extends Agent {

    private long tini;

    protected void setup() {
        //-gui -port 1098 hume:Examen.AgenteHumedad
        tini = System.currentTimeMillis();
        addBehaviour(new miTicker(this, 1000));
    }

    private class miTicker extends TickerBehaviour {

        int minticks;

        public miTicker(Agent a, long intervalo) {
            super(a, intervalo);
            minticks = 0;
        }

        protected void onTick() {
            long tfin = System.currentTimeMillis() - tini;
            int nticks = getTickCount(); // obtenemos el numero de ticks desde el último reset
            minticks++;
            String mensaje = "";
            int valor = generatRandomPositiveNegitiveValue(180, 60);
            /*
             if (valor >= 0 && valor < 20) {
             mensaje = "seco";
             } else if (valor >= 20 && valor < 40) {
             mensaje = "confortable";
             } else if (valor >= 40 && valor < 70) {
             mensaje = "humedo";
             } else if (valor >= 70 && valor <= 100) {
             mensaje = "pegajo";
             }
             */
            //System.out.println(valor+" es "+mensaje);

            mensaje = Integer.toString(valor);

            //------------------ENVIAR
            //System.out.println(getLocalName()+" preparando para enviar mensaje");
            AID id = new AID();
            id.setLocalName("compre");

            ACLMessage msm = new ACLMessage(ACLMessage.INFORM);
            msm.setSender(getAID());
            msm.setLanguage("Español");
            msm.addReceiver(id);
            msm.setContent(mensaje);
            send(msm);

            mensaje = "";
            //System.out.println("Enviando mensaje a "+id.getLocalName());     

        }
    }

    public static int generatRandomPositiveNegitiveValue(int max, int min) {
        //Random rand = new Random();
        int ii = -min + (int) (Math.random() * ((max - (-min)) + 1));
        return ii;
    }

}
