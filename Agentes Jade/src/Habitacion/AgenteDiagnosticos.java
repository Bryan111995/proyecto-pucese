/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Habitacion;


import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;


public class AgenteDiagnosticos extends Agent {
    
    long tini;
    int[] comp = new int[2];
    
    protected void setup() {
        tini = System.currentTimeMillis();
        addBehaviour(new miTicker(this, 2000));
    }
    
    private class miTicker extends TickerBehaviour {
        
        int minticks;
        
        public miTicker(Agent a, long intervalo) {
            super(a, intervalo);
            minticks = 0;
        }
        
        public void reset() {
            super.reset();
            //minticks = 0;
            //System.out.println("reseteo!");
        }
        
        protected void onTick() {
            long tfin = System.currentTimeMillis() - tini;
            int nticks = getTickCount(); // obtenemos el numero de ticks desde el último reset
            minticks++;
            
            String estado = "";
            block();
            ACLMessage msm = receive();
            //System.out.println(getLocalName() + " esta esperando recibir un mensaje");
            if (msm != null) {
                //System.out.println("LLego mensaje de " + msm.getSender().getLocalName());
                if (msm.getSender().getLocalName().equals("fusi")) {
                    comp[0] = Integer.parseInt(msm.getContent());
                }
                if (msm.getSender().getLocalName().equals("hume")) {
                    comp[1] = Integer.parseInt(msm.getContent());
                }

                //Temperatura muy bajo
                if ((comp[0] >= 0 && comp[0] < 10) && (comp[1] >= 0 && comp[1] < 20)) {
                    estado = "Temperatura baja y frecuencia cardiaca anormal";
                } else if ((comp[0] >= 0 && comp[0] < 10) && (comp[1] >= 20 && comp[1] < 40)) {
                    estado = "Temperatura normal y frecuencia cardiaca anormal";
                } else if ((comp[0] >= 0 && comp[0] < 10) && (comp[1] >= 40 && comp[1] < 70)) {
                    estado = "Temperatura normal y frecuencia cardiaca anormal";
                } else if ((comp[0] >= 0 && comp[0] < 10) && (comp[1] >= 70 && comp[1] < 100)) {
                    estado = "Temperatura normal y frecuencia cardiaca anormal";
                } //Temperatura bajo
                else if ((comp[0] >= 10 && comp[0] < 25) && (comp[1] >= 10 && comp[1] < 20)) {
                    estado = "Temperatura normal y frecuencia cardiaca normal";
                } else if ((comp[0] >= 10 && comp[0] < 25) && (comp[1] >= 20 && comp[1] < 40)) {
                    estado = "Temperatura normal y frecuencia cardiaca normal";
                } else if ((comp[0] >= 10 && comp[0] < 25) && (comp[1] >= 40 && comp[1] < 70)) {
                    estado = "Temperatura normal y frecuencia cardiaca normal";
                } else if ((comp[0] >= 10 && comp[0] < 25) && (comp[1] >= 70 && comp[1] < 250)) {
                    estado = "Temperatura normal y frecuencia cardiaca normal";
                } //Temperatura alto
                else if ((comp[0] >= 25 && comp[0] < 35) && (comp[1] >= 25 && comp[1] < 20)) {
                    estado = "Temperatura levemente alta y frecuencia cardiaca levemente alta";
                } else if ((comp[0] >= 25 && comp[0] < 35) && (comp[1] >= 20 && comp[1] < 40)) {
                    estado = "Temperatura levemente alta y frecuencia cardiaca levemente alta";
                } else if ((comp[0] >= 25 && comp[0] < 35) && (comp[1] >= 40 && comp[1] < 70)) {
                    estado = "Temperatura levemente alta y frecuencia cardiaca levemente alta";
                } else if ((comp[0] >= 25 && comp[0] < 35) && (comp[1] >= 70 && comp[1] < 350)) {
                    estado = "Temperatura levemente alta y frecuencia cardiaca levemente alta";
                } //Temperatura muy alto
                else if ((comp[0] >= 35 && comp[0] < 45) && (comp[1] >= 35 && comp[1] < 20)) {
                    estado = "Temperatura  alta y frecuencia cardiaca muy alta";
                } else if ((comp[0] >= 35 && comp[0] < 45) && (comp[1] >= 20 && comp[1] < 40)) {
                    estado = "Temperatura  alta y frecuencia cardiaca muy alta";
                } else if ((comp[0] >= 35 && comp[0] < 45) && (comp[1] >= 40 && comp[1] < 70)) {
                    estado = "Temperatura  alta y frecuencia cardiaca muy alta";
                } else if ((comp[0] >= 35 && comp[0] < 45) && (comp[1] >= 70 && comp[1] < 450)) {
                    estado = "Temperatura  alta y frecuencia cardiaca muy alta";
                }
            }
            
            if (nticks == 3) {
                if (!estado.equals("")) {
                    System.out.println("----------------------------------------");
                    System.out.println("Temperatura: " + comp[0] +"C°"+ "   "
                            + "Cardio: " + comp[1]);
                    System.out.println("-->El diagnostico del paciente es " + estado);
                    estado = "";
                }
                reset();
            } /*else {
                System.out.println(nticks + "    " + minticks);
            }*/
            
            /*
             //Matar al agente
             if(minticks == 40){
             myAgent.doDelete();
             }*/
        }
    }
}
