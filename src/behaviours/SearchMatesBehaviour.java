package behaviours;

import agents.TinderAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.util.leap.Iterator;

public class SearchMatesBehaviour extends Behaviour {
    TinderAgent tinderAgent;

    public SearchMatesBehaviour(TinderAgent tinderAgent) {
        this.tinderAgent = tinderAgent;
    }

    public void action() {
        // Search the other agents in the same localisation
        System.out.println("Agent "+ tinderAgent.getLocalName()+" searching for agents in localisation " + tinderAgent.getLocalisation());
        try {
            // Build the description used as template for the search
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription templateSd = new ServiceDescription();
            templateSd.setType("tinder-client");
            template.addServices(templateSd);

            //tinderAgent.send(DFService.createSubscriptionMessage(tinderAgent, tinderAgent.getDefaultDF(), template, null    ));

            DFAgentDescription[] results = DFService.search(tinderAgent, template);
            if (results.length > 0) {
                System.out.println("Agent "+tinderAgent.getLocalName()+" found the following people:");
                for (int i = 0; i < results.length; ++i) {
                    DFAgentDescription dfd = results[i];
                    AID provider = dfd.getName();
                    Iterator it = dfd.getAllServices();
                    while (it.hasNext()) {
                        ServiceDescription sd = (ServiceDescription) it.next();
                        if (sd.getType().equals("tinder-client") && !provider.equals(tinderAgent.getAID())) {
                            Iterator it2 = sd.getAllProperties();
                            while (it2.hasNext()) {
                                Property property = (Property) it2.next();
                                if(property.match(new Property("localisation", Integer.toString(tinderAgent.getLocalisation())))) {
                                //if(property.getName().equals("localisation") && property.getValue().equals(tinderAgent.getLocalisation())){
                                    if(!tinderAgent.getTinderAgents().contains(provider)) {
                                        System.out.println(provider.getLocalName());
                                        tinderAgent.getTinderAgents().add(provider);
                                    }
                                }

                            }

                        }
                    }
                }
            }
            else {
                System.out.println("Agent "+tinderAgent.getLocalName()+" did not find any weather-forecast service");
            }
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }


    // Lorsque l'agent se termine, il est retiré de la collection
    public boolean done() {
        return true;
    }
}
