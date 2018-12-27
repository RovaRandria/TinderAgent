package behaviours;

import agents.TinderAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class FirstMatchBehaviour extends Behaviour {
    TinderAgent tinderAgent;

    public FirstMatchBehaviour(TinderAgent tinderAgent) {
        this.tinderAgent = tinderAgent;
    }

    public void action() {
        TinderAgent tinderAgent2 = new TinderAgent();
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(tinderAgent2.getAID());
        msg.setContent("Coucou");
        tinderAgent.send(msg);

        if(tinderAgent2.match(tinderAgent)) {
            ACLMessage matchMsg = new ACLMessage(ACLMessage.PROPOSE);
            matchMsg.addReceiver(tinderAgent2.getAID());
            matchMsg.setContent("Je match");
            tinderAgent.send(matchMsg);

            if(tinderAgent.match(tinderAgent2)) {
                ACLMessage matchBackMsg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                matchBackMsg.addReceiver(tinderAgent.getAID());
                matchBackMsg.setContent("Je match aussi");
                tinderAgent2.send(matchBackMsg);
                done();
            }
            else {
                ACLMessage matchRejectedMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                matchRejectedMsg.addReceiver(tinderAgent.getAID());
                matchRejectedMsg.setContent("Pas moi");
                tinderAgent2.send(matchRejectedMsg);
            }
        }

        else {
            ACLMessage matchMsg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(tinderAgent2.getAID());
            msg.setContent("Bye");
        }

    }

    public boolean done() {
        return true;
    }
}
