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

import jade.core.Agent;

/**
   This example shows a minimal agent that just prints "Hallo World!" 
   and then terminates.
   @author Giovanni Caire - TILAB
 */
 

 
 
 
 
public class TinderAgent extends Agent {

 enum Genre {F,M};
 enum Orientation {STRAIGHT, BISEXUAL, HOMO};
 int[2] localisation;
 enum Sociability {VL,L,N,H,VH}
 enum Seriosity {VL,L,N,H,VH}
 enum Sportivity {VL,L,N,H,VH}
 enum Culture {VL,L,N,H,VH}
 
 enum Sociability_wanted_min{VL,L,N,H,VH}
 enum Sociability_wanted_max{VL,L,N,H,VH}
 
 enum Seriosity_wanted_min{VL,L,N,H,VH}
 enum Seriosity_wanted_max{VL,L,N,H,VH}
 
 enum Sportivity_wanted_min{VL,L,N,H,VH}
 enum Sportivity_wanted_max{VL,L,N,H,VH}
 
 enum Culture_wanted_min{VL,L,N,H,VH}
 enum Culture_wanted_max{VL,L,N,H,VH}
  
  protected void setup() {
  	System.out.println("Hello World! My name is "+getLocalName());
  	
  	// Make this agent terminate
  	doDelete();
  } 
}

