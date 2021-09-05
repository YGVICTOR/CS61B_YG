public class NBody{
	public static double readRadius(String fileName){
		In in = new In(fileName);
		int planetNumber = in.readInt();
		double radius = in.readDouble();
		return radius;
	}


	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);
		int planetNumber = in.readInt();
		double radius = in.readDouble();
		int count = 0;
		Planet planets[] = new Planet[planetNumber];
		while(!in.isEmpty()){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel =  in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();
			planets[count++] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,img);
			if(count == planetNumber){
				break;
			}
		}
		return planets;
	}
	
	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet planets[] = readPlanets(filename);
		StdDraw.setScale(-1*radius/(1e11), radius/(1e11));
		StdDraw.picture(0,0,"images/starfield.jpg");
		for(Planet planet : planets){
			planet.draw();
		}
		/**a graphics technique to prevent flickering in the animation*/
		StdDraw.enableDoubleBuffering();
		double time = 0;
		while(time <= T){
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			int i = 0;
			for(Planet planet : planets){
				xForces[i] = planet.calcNetForceExertedByX(planets);
				yForces[i] = planet.calcNetForceExertedByY(planets);
				i++;
			}
			i = 0;
			int j = 0;
			for(Planet planet : planets){
				planet.update(dt, xForces[i++], yForces[j++]);
			}
			StdDraw.picture(0,0,"images/starfield.jpg");
			for(Planet planet : planets){
				planet.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  		planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  		planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}

	}
}