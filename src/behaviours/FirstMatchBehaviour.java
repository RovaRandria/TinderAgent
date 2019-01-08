package behaviours;

import agents.TinderAgent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class FirstMatchBehaviour extends CyclicBehaviour {
    TinderAgent tinderAgent;
    //TinderSupervisorAgent tinderSupervisorAgent;

    public FirstMatchBehaviour(TinderAgent tinderAgent) {
        this.tinderAgent = tinderAgent;
    }

    public void action() {
        ACLMessage msg = null;
        msg = tinderAgent.receive();
        //TODO stocker agents trié par loc
        if (msg != null) {
            switch (msg.getPerformative()) {
                // Si je reçois une approche avec des caractéristiques
                case ACLMessage.INFORM:
                    // Si la personne me plait, j'envoie les miennes
                    System.out.println(tinderAgent.decodeCaracs(msg.getContent()));
                    if (tinderAgent.match(tinderAgent.decodeCaracs(msg.getContent()))) {
                        ACLMessage matchMsg = new ACLMessage(ACLMessage.PROPOSE);
                        matchMsg.addReceiver(msg.getSender());
                        matchMsg.setContent(tinderAgent.encodeCaracs());
                        tinderAgent.send(matchMsg);
                        System.out.println(tinderAgent.getLocalName() + " matches with " + msg.getSender().getLocalName());
                    }
                    // Sinon je dis que je ne suis pas interesse
                    else {
                        ACLMessage matchRejectedMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                        matchRejectedMsg.addReceiver(msg.getSender());
                        matchRejectedMsg.setContent("Not interested");
                        tinderAgent.send(matchRejectedMsg);
                        System.out.println(tinderAgent.getLocalName() + " is not interested by " + msg.getSender().getLocalName());
                    }
                    break;
                // Si je reçois une proposition
                case ACLMessage.PROPOSE:
                    // Si la personne match mes criteres, il y a match entre les 2 agents
                    if (tinderAgent.match(tinderAgent.decodeCaracs(msg.getContent()))) {
                        ACLMessage matchMsg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                        matchMsg.addReceiver(msg.getSender());
                        matchMsg.setContent("MATCH");
                        tinderAgent.send(matchMsg);
                        System.out.println(tinderAgent.getLocalName() + " and " + msg.getSender().getLocalName() + " both match");
                        tinderAgent.doDelete();
                    }
                    // Sinon, je dis que je ne suis pas interesse
                    else {
                        ACLMessage matchRejectedMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                        matchRejectedMsg.addReceiver(msg.getSender());
                        matchRejectedMsg.setContent("Not interested");
                        tinderAgent.send(matchRejectedMsg);
                        System.out.println(tinderAgent.getLocalName() + " is not interested by " + msg.getSender().getLocalName());
                    }
                    break;
                // Si je reçois un refus, je contact une autre personne
                case ACLMessage.REJECT_PROPOSAL:
                    tinderAgent.setAgentToContact(tinderAgent.getAgentToContact() + 1);
                    break;
                // Si on accepte une de mes propositions, je me termine
                case ACLMessage.ACCEPT_PROPOSAL:
                    tinderAgent.doDelete();
                    break;
                default:
                    System.out.println(msg.getSender().getLocalName() + " send " + msg.getContent());
                    break;
            }
            //TODO stocker agents trié par loc
        }

        // Pour chaque agent de notre liste
        if (tinderAgent.getAgentToContact() > tinderAgent.getLastContactedAgent() && tinderAgent.getTinderAgents().size() > 0 && tinderAgent.getAgentToContact() + 1 <= tinderAgent.getTinderAgents().size()) {
            AID aid = tinderAgent.getTinderAgents().get(tinderAgent.getAgentToContact());
            // Je lui dis bonjour
            ACLMessage hello = new ACLMessage(ACLMessage.INFORM);
            hello.addReceiver(aid);
            hello.setContent(tinderAgent.encodeCaracs());
            tinderAgent.send(hello);
            tinderAgent.setLastContactedAgent(tinderAgent.getLastContactedAgent() + 1);
            System.out.println(tinderAgent.getLocalName() + " has contacted " + aid.getLocalName());
            System.out.println(hello.getContent());
        }

    }

}
