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
import behaviours.SearchMatesBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.Property;

import java.util.ArrayList;
import java.util.HashMap;

/**
 This example shows a minimal agent that just prints "Hallo World!"
 and then terminates.
 @author Giovanni Caire - TILAB
 */

public class TinderAgent extends Agent {
    private ArrayList<AID> tinderAgents;
    private int agentToContact;
    private int lastContactedAgent;
    private AID aid;
    private HashMap<AID, Float> contactedAgents;
    //private Point localisation;
    private int localisation;
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
        this.agentToContact = 0;
        this.lastContactedAgent = -1;
        this.tinderAgents = new ArrayList<>();
        this.contactedAgents = new HashMap<AID, Float>();
        this.mateScore = 0;

        //int randomX = minPosition + (int)(Math.random() * ((maxPosition - minPosition) + 1));
        //int randomY = minPosition + (int)(Math.random() * ((maxPosition - minPosition) + 1));
        //this.localisation = new Point(randomX, randomY);
        this.localisation = (int)(Math.random() * 5);
        this.genre = allGenres[(int) (Math.random() * 2)];
        this.orientation = allOrientations[(int)(Math.random() * 3)];
        this.sociability = (int)(Math.random() * 5);
        this.seriosity = (int)(Math.random() * 5);
        this.sportivity = (int)(Math.random() * 5);
        this.culture = (int)(Math.random() * 5);

        this.sociabilityWantedMin = (int)(Math.random() * 2);
        this.sociabilityWantedMax = (int)(Math.random() * 2) + 3;

        this.seriosityWantedMin = (int)(Math.random() * 2);
        this.seriosityWantedMax =(int)(Math.random() * 2) + 3;

        this.sportivityWantedMin = (int)(Math.random() * 2);
        this.sportivityWantedMax = (int)(Math.random() * 2) + 3;

        this.cultureWantedMin = (int)(Math.random() * 2);
        this.cultureWantedMax = (int)(Math.random() * 2) + 3;

    }

    public TinderAgent(int localisation, Genre genre, Orientation orientation, int sociability, int seriosity, int sportivity, int culture, int sociabilityWantedMin, int sociabilityWantedMax, int seriosityWantedMin, int seriosityWantedMax, int sportivityWantedMin, int sportivityWantedMax, int cultureWantedMin, int cultureWantedMax) {
        this.agentToContact = 0;
        this.lastContactedAgent = -1;
        this.tinderAgents = new ArrayList<>();
        this.contactedAgents = new HashMap<AID, Float>();
        this.mateScore = 0;

        this.localisation = localisation;
        this.genre = genre;
        this.orientation = orientation;
        this.sociability = sociability;
        this.seriosity = seriosity;
        this.sportivity = sportivity;
        this.culture = culture;
        this.sociabilityWantedMin = sociabilityWantedMin;
        this.sociabilityWantedMax = sociabilityWantedMax;
        this.seriosityWantedMin = seriosityWantedMin;
        this.seriosityWantedMax = seriosityWantedMax;
        this.sportivityWantedMin = sportivityWantedMin;
        this.sportivityWantedMax = sportivityWantedMax;
        this.cultureWantedMin = cultureWantedMin;
        this.cultureWantedMax = cultureWantedMax;
    }

    protected void setup() {
        Object[] args = getArguments();
        if(args.length > 0)
            localisation = Integer.valueOf((String) args[0]);
        if(args.length > 1)
            genre = Genre.valueOf(args[1].toString());
        if(args.length > 2)
            orientation = Orientation.valueOf(args[2].toString());
        if(args.length > 3)
            sociability = Integer.valueOf((String) args[3]);
        if(args.length > 4)
            seriosity = Integer.valueOf((String) args[4]);
        if(args.length > 5)
            sportivity = Integer.valueOf((String) args[5]);
        if(args.length > 6)
            culture = Integer.valueOf((String) args[6]);
        if(args.length > 7)
            sociabilityWantedMin = Integer.valueOf((String) args[7]);
        if(args.length > 8)
            sociabilityWantedMax = Integer.valueOf((String) args[8]);
        if(args.length > 9)
            seriosityWantedMin = Integer.valueOf((String) args[9]);
        if(args.length > 10)
            seriosityWantedMax = Integer.valueOf((String) args[10]);
        if(args.length > 11)
            sportivityWantedMin = Integer.valueOf((String) args[11]);
        if(args.length > 12)
            sportivityWantedMax = Integer.valueOf((String) args[12]);
        if(args.length > 13)
            cultureWantedMin = Integer.valueOf((String) args[13]);
        if(args.length > 14)
            cultureWantedMax = Integer.valueOf((String) args[14]);

        System.out.println("Hello Tinder ! I'm " + getAID().getLocalName());
        System.out.println("Sex : " + genre);
        System.out.println("Orientation : " + orientation);
        System.out.println("Sociability : " + sportivity);
        System.out.println("Seriosity : " + seriosity);
        System.out.println("Sportivity : " + sportivity);
        System.out.println("Culture : " + culture);
        System.out.println("sociabilityWantedMin : " + sociabilityWantedMin);
        System.out.println("sociabilityWantedMax : " + sociabilityWantedMax);
        System.out.println("seriosityWantedMin : " + seriosityWantedMin);
        System.out.println("seriosityWantedMax : " + seriosityWantedMax);
        System.out.println("sportivityWantedMin : " + sportivityWantedMin);
        System.out.println("sportivityWantedMax : " + sportivityWantedMax);
        System.out.println("cultureWantedMin : " + cultureWantedMin);
        System.out.println("cultureWantedMax : " + cultureWantedMax);

        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName(getAID().getLocalName());
            sd.setType("tinder-client");
            sd.addOntologies("tinder-ontology");
            // Agents that want to use this service need to "speak" the FIPA-SL language
            sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
            sd.addProperties(new Property("localisation", localisation));
            dfd.addServices(sd);

            DFService.register(this, dfd);

            System.out.println("Agent "+getAID().getLocalName()+" has joined Tinder");

        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }


        addBehaviour(new SearchMatesBehaviour(this));
        addBehaviour(new FirstMatchBehaviour(this));

        // Make this agent terminate
        //doDelete();
    }

    protected void takeDown() {
        try
        {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        } System.out.println("Tinder agent " + getAID().getLocalName() + " terminating");
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

    public String encodeCaracs() {
        String message ="";
        message+=getGenre() +" ";
        message+=getOrientation()+" ";
        message+=getSociability()+" ";
        message+=getSeriosity()+" ";
        message+=getSportivity()+" ";
        message+=getCulture()+" ";
        message+=getSociabilityWantedMin()+" ";
        message+=getSociabilityWantedMax()+" ";
        message+=getSeriosityWantedMin()+" ";
        message+=getSeriosityWantedMin()+" ";
        message+=getSportivityWantedMin()+" ";
        message+=getSportivityWantedMin()+" ";
        message+=getCultureWantedMin()+" ";
        message+=getCultureWantedMin();

        return message;
    }

    public TinderAgent decodeCaracs(String txt){
        String[] list = txt.split(" ");
        TinderAgent td = new TinderAgent(0, Genre.valueOf(list[0]), Orientation.valueOf(list[1]), Integer.parseInt(list[2]), Integer.parseInt(list[3]), Integer.parseInt(list[4]),
                Integer.parseInt(list[5]), Integer.parseInt(list[6]), Integer.parseInt(list[7]), Integer.parseInt(list[8]), Integer.parseInt(list[9]), Integer.parseInt(list[10]),
                Integer.parseInt(list[11]), Integer.parseInt(list[12]), Integer.parseInt(list[13]));

        return td;
    }

    public ArrayList<AID> getTinderAgents() {
        return tinderAgents;
    }

    public void setTinderAgents(ArrayList<AID> tinderAgents) {
        this.tinderAgents = tinderAgents;
    }

    public HashMap<AID, Float> getContactedAgents() {
        return contactedAgents;
    }

    public void setContactedAgents(HashMap<AID, Float> contactedAgents) {
        this.contactedAgents = contactedAgents;
    }

    public int getLocalisation() {
        return localisation;
    }

    public void setLocalisation(int localisation) {
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

    public int getAgentToContact() {
        return agentToContact;
    }

    public void setAgentToContact(int agentToContact) {
        this.agentToContact = agentToContact;
    }

    public int getLastContactedAgent() {
        return lastContactedAgent;
    }

    public void setLastContactedAgent(int lastContactedAgent) {
        this.lastContactedAgent = lastContactedAgent;
    }
}

