package agents;

import jade.core.AID;
import jade.core.Agent;

import java.util.HashMap;
import java.util.Map;

public class TinderSupervisorAgent extends Agent {
    private HashMap<AID, TinderAgent> tinderAgents;

    public TinderSupervisorAgent() {
        for(int i =0; i<100; i++) {
            TinderAgent tinderAgent = new TinderAgent(this);
            tinderAgents.put(tinderAgent.getAid(), tinderAgent);
        }
    }

    public HashMap<AID, TinderAgent> getTinderAgents() {
        return tinderAgents;
    }

    public void setTinderAgents(HashMap<AID, TinderAgent> tinderAgents) {
        this.tinderAgents = tinderAgents;
    }

    public TinderAgent nearestAgent(TinderAgent tinderAgent) {
        TinderAgent nearestTinderAgent = null;
        double distanceMin = 1000;

        for (Map.Entry<AID, TinderAgent> agent : tinderAgents.entrySet()) {
            if(agent.getValue().getAid() != tinderAgent.getAid()) {
                double distance = Math.hypot(tinderAgent.getLocalisation().getX() - agent.getValue().getLocalisation().getX(),
                        tinderAgent.getLocalisation().getY() - agent.getValue().getLocalisation().getY());
                if (distance < distanceMin) {
                    distanceMin = distance;
                    nearestTinderAgent = agent.getValue();
                }
            }
        }
        return nearestTinderAgent;
    }
}
