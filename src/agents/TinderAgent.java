package agents;

/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 *
 * GNU Lesser General Public License
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */

import behaviours.FirstMatchBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.proto.SubscriptionInitiator;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 This example shows a minimal agent that just prints "Hallo World!"
 and then terminates.
 @author Giovanni Caire - TILAB
 */

public class TinderAgent extends Agent {
    private ArrayList<AID> tinderAgents;
    private AID aid;
    private HashMap<AID, Float> contactedAgents;
    private Point localisation;
    private Genre genre;
    private Orientation orientation;
    private int sociability;
    private int seriosity;
    private int sportivity;
    private int culture;
    private int sociabilityWantedMin;
    private int sociabilityWantedMax;
    private int seriosityWantedMin;
    private int seriosityWantedMax;
    private int sportivityWantedMin;
    private int sportivityWantedMax;
    private int cultureWantedMin;
    private int cultureWantedMax;
    private float mateScore;

    public enum Genre {F, M}
    public enum Orientation {HOMO, BISEXUAL, STRAIGHT}

    Genre[] allGenres = Genre.values();
    Orientation[] allOrientations = Orientation.values();
    int minPosition = -30;
    int maxPosition = 30;

    //random generation
    public TinderAgent(){
        this.tinderAgents = new ArrayList<>();
        this.aid = new AID();
        this.contactedAgents = new HashMap<AID, Float>();
        int randomX = minPosition + (int)(Math.random() * ((maxPosition - minPosition) + 1));
        int randomY = minPosition + (int)(Math.random() * ((maxPosition - minPosition) + 1));
        this.localisation = new Point(randomX, randomY);
        this.genre = allGenres[(int) (Math.random() * 2)];
        this.orientation = allOrientations[(int)(Math.random() * 3)];
        this.sociability = (int)(Math.random() * 5);
        this.seriosity = (int)(Math.random() * 5);
        this.sportivity = (int)(Math.random() * 5);
        this.culture = (int)(Math.random() * 5);

        this.sociabilityWantedMin = (int)(Math.random() * 5);
        this.sociabilityWantedMax = sociabilityWantedMin + (int)(Math.random() * ((4 - sociabilityWantedMin) + 1));

        this.seriosityWantedMin = (int)(Math.random() * 5);
        this.seriosityWantedMax = seriosityWantedMin + (int)(Math.random() * ((4 - seriosityWantedMin) + 1));

        this.sportivityWantedMin = (int)(Math.random() * 5);
        this.sportivityWantedMax = sportivityWantedMin + (int)(Math.random() * ((4 - sportivityWantedMin) + 1));

        this.cultureWantedMin = (int)(Math.random() * 5);
        this.cultureWantedMax = cultureWantedMin + (int)(Math.random() * ((4 - cultureWantedMin) + 1));

        this.mateScore = 0;
    }

    protected void setup() {
        System.out.println("Hello World! I'm tinder agent " + aid.toString());

        // Search for services of type "tinder"
        System.out.println("Agent "+getLocalName()+" searching for services of type \"tinder\"");
        try {
            // Build the description used as template for the search
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription templateSd = new ServiceDescription();
            templateSd.setType("tinder");
            template.addServices(templateSd);

            SearchConstraints sc = new SearchConstraints();
            // We want to receive 10 results at most
            sc.setMaxResults(new Long(10));

            DFAgentDescription[] results = DFService.search(this, template, sc);
            if (results.length > 0) {
                System.out.println("Agent "+getLocalName()+" found the following tinder services:");
                for (int i = 0; i < results.length; ++i) {
                    DFAgentDescription dfd = results[i];
                    AID provider = dfd.getName();
                    // The same agent may provide several services; we are only interested
                    // in the tinder one
                    Iterator it = dfd.getAllServices();
                    while (it.hasNext()) {
                        ServiceDescription sd = (ServiceDescription) it.next();
                        if (sd.getType().equals("tinder")) {
                            ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
                            msg.addReceiver(provider);
                            msg.setContent(localisation.x + "," + localisation.y);
                            System.out.println("Localisation sent");
                            send(msg);
                            break;
                        }

                    }
                }
            }
            else {
                System.out.println("Agent "+getLocalName()+" did not find any tinder service");
            }
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        //addBehaviour(new FirstMatchBehaviour(this));

        // Make this agent terminate
        //doDelete();
    }

    protected void takeDown() {
        System.out.println("Tinder agent " + aid.toString() + " terminating");
    }

    public boolean match(TinderAgent agent) {
        switch(orientation) {
            case HOMO:
                if(agent.getGenre() != genre || (agent.getOrientation() == orientation.STRAIGHT))
                    return false;
                break;
            case BISEXUAL:
                if(agent.getOrientation() == orientation.HOMO && genre != agent.getGenre() || agent.getOrientation() == orientation.STRAIGHT && genre == agent.getGenre() )
                    return false;
                break;
            case STRAIGHT:
                if(agent.getGenre() == genre || (agent.getOrientation() == orientation.HOMO))
                    return false;
                break;
        }
        if(agent.getSociability() < sociabilityWantedMin || agent.getSociability() > sociabilityWantedMax)
            return false;
        if(agent.getSeriosity() < seriosityWantedMin || agent.getSeriosity() > seriosityWantedMax)
            return false;
        if(agent.getSportivity() < sportivityWantedMin || agent.getSportivity() > sportivityWantedMax)
            return false;
        if(agent.getCulture() < cultureWantedMin || agent.getCulture() > cultureWantedMax)
            return false;

        return true;
    }

    public Float matchScore(TinderAgent agent) {
        if(match(agent)) {
            return 100f;
        }
        else return 0f;
    }

    public ArrayList<AID> getTinderAgents() {
        return tinderAgents;
    }

    public void setTinderAgents(ArrayList<AID> tinderAgents) {
        this.tinderAgents = tinderAgents;
    }

    public AID getAid() {
        return aid;
    }

    public void setAid(AID aid) {
        this.aid = aid;
    }

    public HashMap<AID, Float> getContactedAgents() {
        return contactedAgents;
    }

    public void setContactedAgents(HashMap<AID, Float> contactedAgents) {
        this.contactedAgents = contactedAgents;
    }

    public Point getLocalisation() {
        return localisation;
    }

    public void setLocalisation(Point localisation) {
        this.localisation = localisation;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getSociability() {
        return sociability;
    }

    public void setSociability(int sociability) {
        this.sociability = sociability;
    }

    public int getSeriosity() {
        return seriosity;
    }

    public void setSeriosity(int seriosity) {
        this.seriosity = seriosity;
    }

    public int getSportivity() {
        return sportivity;
    }

    public void setSportivity(int sportivity) {
        this.sportivity = sportivity;
    }

    public int getCulture() {
        return culture;
    }

    public void setCulture(int culture) {
        this.culture = culture;
    }

    public int getSociabilityWantedMin() {
        return sociabilityWantedMin;
    }

    public void setSociabilityWantedMin(int sociabilityWantedMin) {
        this.sociabilityWantedMin = sociabilityWantedMin;
    }

    public int getSociabilityWantedMax() {
        return sociabilityWantedMax;
    }

    public void setSociabilityWantedMax(int sociabilityWantedMax) {
        this.sociabilityWantedMax = sociabilityWantedMax;
    }

    public int getSeriosityWantedMin() {
        return seriosityWantedMin;
    }

    public void setSeriosityWantedMin(int seriosityWantedMin) {
        this.seriosityWantedMin = seriosityWantedMin;
    }

    public int getSeriosityWantedMax() {
        return seriosityWantedMax;
    }

    public void setSeriosityWantedMax(int seriosityWantedMax) {
        this.seriosityWantedMax = seriosityWantedMax;
    }

    public int getSportivityWantedMin() {
        return sportivityWantedMin;
    }

    public void setSportivityWantedMin(int sportivityWantedMin) {
        this.sportivityWantedMin = sportivityWantedMin;
    }

    public int getSportivityWantedMax() {
        return sportivityWantedMax;
    }

    public void setSportivityWantedMax(int sportivityWantedMax) {
        this.sportivityWantedMax = sportivityWantedMax;
    }

    public int getCultureWantedMin() {
        return cultureWantedMin;
    }

    public void setCultureWantedMin(int cultureWantedMin) {
        this.cultureWantedMin = cultureWantedMin;
    }

    public int getCultureWantedMax() {
        return cultureWantedMax;
    }

    public void setCultureWantedMax(int cultureWantedMax) {
        this.cultureWantedMax = cultureWantedMax;
    }

    public float getMateScore() {
        return mateScore;
    }

    public void setMateScore(float mateScore) {
        this.mateScore = mateScore;
    }
}

