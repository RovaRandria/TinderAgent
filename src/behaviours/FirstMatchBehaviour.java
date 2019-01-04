package behaviours;

import agents.TinderAgent;
import agents.TinderSupervisorAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class FirstMatchBehaviour extends Behaviour {
    TinderAgent tinderAgent;
    //TinderSupervisorAgent tinderSupervisorAgent;

    public FirstMatchBehaviour(TinderAgent tinderAgent) {
        this.tinderAgent = tinderAgent;
    }

    public void action() {


        ACLMessage reply = null;
        reply = tinderAgent.receive();
        if(reply == null) block();

        //TODO stocker agents trié par loc
        if(reply.getPerformative() == ACLMessage.INFORM) {
                //TODO stocker agents trié par loc
                System.out.println("liste agents reçus");
        }


//        while(true) {
//            TinderAgent nearestTinderAgent = tinderSupervisorAgent.nearestAgent(tinderAgent);
//
//            // Si l'agent le plus proche de n'a jamais été en contact, l'agent lui dit boujour
//            if(!tinderAgent.getContactedAgents().containsKey(nearestTinderAgent.getAid())) {
//
//                // Je lui dis bonjour
//                ACLMessage hello = new ACLMessage(ACLMessage.INFORM);
//                hello.addReceiver(nearestTinderAgent.getAID());
//                hello.setContent("Coucou");
//                tinderAgent.send(hello);
//
//                // Si l'autre agent match ses critères, l'agent lui envoie une proposition
//                if(tinderAgent.match(nearestTinderAgent)) {
//                    ACLMessage matchMsg = new ACLMessage(ACLMessage.PROPOSE);
//                    matchMsg.addReceiver(nearestTinderAgent.getAID());
//                    matchMsg.setContent("Je match");
//                    tinderAgent.send(matchMsg);
//                }
//            }
//
//            // L'agent reçoit un message
//            ACLMessage receivedMsg = tinderAgent.receive();
//            if(receivedMsg != null) {
//                AID sender = receivedMsg.getSender();
//                switch (receivedMsg.getPerformative()) {
//
//                    // Si c'est une proposition
//                    case ACLMessage.PROPOSE:
//
//                        // Si les 2 agents matchent, l'agent accepte sa proposition et l'agent se termine
//                        if(tinderAgent.match(tinderSupervisorAgent.getTinderAgents().get(sender))) {
//                            ACLMessage acceptMsg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
//                            acceptMsg.addReceiver(sender);
//                            acceptMsg.setContent("Je match aussi");
//                            tinderAgent.send(acceptMsg);
//
//                            if(!tinderAgent.getContactedAgents().containsKey(sender)) {
//                                tinderAgent.getContactedAgents().put(sender, tinderAgent.matchScore(tinderSupervisorAgent.getTinderAgents().get(sender)));
//                            }
//
//                            done();
//                        }
//
//                        // Sinon il refuse sa proposition, on l'ajoute dans la liste des agents contactés avec un score nul
//                        else {
//                            ACLMessage matchRejectedMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
//                            matchRejectedMsg.addReceiver(sender);
//                            matchRejectedMsg.setContent("Pas moi");
//                            tinderAgent.send(matchRejectedMsg);
//
//                            if(!tinderAgent.getContactedAgents().containsKey(sender)) {
//                                tinderAgent.getContactedAgents().put(sender, tinderAgent.matchScore(tinderSupervisorAgent.getTinderAgents().get(sender)));
//                            }
//
//                        }
//                        break;
//                    // Si un autre agent a accepté une proposition, l'agent se termine
//                    case ACLMessage.ACCEPT_PROPOSAL:
//                        done();
//                        break;
//
//                    // Si un autre agent a refusé la proposition, on l'ajoute dans la liste des agents contactés avec un score nul
//                    case ACLMessage.REJECT_PROPOSAL:
//                        if(!tinderAgent.getContactedAgents().containsKey(sender)) {
//                            tinderAgent.getContactedAgents().put(sender, tinderAgent.matchScore(tinderSupervisorAgent.getTinderAgents().get(sender)));
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }

        done();
    }

    // Lorsque l'agent se termine, il est retiré de la collection
    public boolean done() {
        return true;
    }
}
