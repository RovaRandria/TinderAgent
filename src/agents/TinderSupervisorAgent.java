package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TinderSupervisorAgent extends Agent {
    private HashMap<AID, Point> tinderAgents = new HashMap<>();

    protected void setup() {
        System.out.println("Hello World! I'm a new tinder supervisor "+getAID());

        String serviceName = "unknown";

        // Read the name of the service to register as an argument
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            serviceName = (String) args[0];
        }

        // Register the service
        System.out.println("Agent "+getAID()+" registering service \""+serviceName+"\" of type \"tinder\"");
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName(serviceName);
            sd.setType("tinder");
            // Agents that want to use this service need to "know" the weather-forecast-ontology
            sd.addOntologies("tinder");
            // Agents that want to use this service need to "speak" the FIPA-SL language
            sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
            sd.addProperties(new Property("country", "France"));
            dfd.addServices(sd);

            DFService.register(this, dfd);

            System.out.println("Agent "+getAID()+" has registered service \""+serviceName+"\" of type \"tinder\"");

        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        while(true) {
            // L'agent reçoit un message d'enregistrement
            MessageTemplate modele = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
            ACLMessage msg = this.receive(modele);
            if(msg != null) {
                String[] localisation = msg.getContent().split(",");
                Point loc = new Point(Integer.parseInt(localisation[0]), Integer.parseInt(localisation[1]));
                tinderAgents.put(msg.getSender(), loc);
                System.out.println("Agent "+msg.getSender()+ " is at " + msg.getContent());
                System.out.println("Agent "+msg.getSender()+ " has suscribed registered service.");

                ACLMessage agentsSortByLoc = new ACLMessage(ACLMessage.INFORM);
                agentsSortByLoc.addReceiver(msg.getSender());
                ArrayList<AID> nearestAgents = nearestAgents(loc, msg.getSender());
                agentsSortByLoc.setContent(nearestAgents.toString());
                send(agentsSortByLoc);
                System.out.println("Agents near "+msg.getSender()+ " " + nearestAgents.toString());

            }
        }
    }

    protected void takeDown() {
        System.out.println("Tinder supervisor terminating");
    }

    public ArrayList<AID> nearestAgents(Point point, AID aid) {
        ArrayList<AID> al = new ArrayList<>();
        ArrayList<AID> agentlist = new ArrayList<AID>();
        ArrayList<Point> pointlist = new ArrayList<Point>();
        double distanceMin;
        int nearestAgent;

        for (Map.Entry<AID, Point> agent : tinderAgents.entrySet()) {
            if(agent.getKey() != aid) {
                agentlist.add(agent.getKey());
                pointlist.add(agent.getValue());
            }
        }

        while (agentlist.size()>0) {
            nearestAgent=0;
            distanceMin = 1000;
            for (int j=0; j<agentlist.size(); j++)  {
                double distance = Math.hypot(pointlist.get(j).x - point.x,
                        pointlist.get(j).y - point.y);
                if (distance < distanceMin) {
                    distanceMin=distance;
                    nearestAgent = j;
                }
            }
            al.add(agentlist.get(nearestAgent));
            agentlist.remove(nearestAgent);
            pointlist.remove(nearestAgent);
        }

        return al;
    }
}
