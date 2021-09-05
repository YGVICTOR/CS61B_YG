public class Planet{
	public double xxPos;//Its current x position;
	public double yyPos;//Its current y position;
	public double xxVel;//Its current velocity in the x direction;
	public double yyVel;//Its current velocity in the y direction;
	public double mass;//Its mass;
	public String imgFileName;//The name of the file that corresponds to the image that depicts the planet
	public static final double G = 6.67*Math.pow(10,-11);


	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p){
		double dx = this.xxPos - p.xxPos;
		double dy = this.yyPos - p.yyPos;
		double distance = dx*dx + dy*dy;
		return Math.sqrt(distance);
	}

	public double calcForceExertedBy(Planet planet){
		double distance = calcDistance(planet);
		double force = (G * this.mass * planet.mass)/(distance * distance);
		return force;
	}

	public double calcForceExertedByX(Planet planet){
		double force = calcForceExertedBy(planet);
		double distance = calcDistance(planet);
		double distanceX = planet.xxPos - this.xxPos;
		double forceX = (force * distanceX) / distance;
		return forceX;
	}

	public double calcForceExertedByY(Planet planet){
		double force = calcForceExertedBy(planet);
		double distance = calcDistance(planet);
		double distanceY = planet.yyPos - this.yyPos;
		double forceY = (force * distanceY) / distance;
		return forceY;
	}

	public double calcNetForceExertedByX(Planet[] planets){
		double totalX = 0;
		for(Planet planet : planets){
			if(! this.equals(planet)){
				double forceX = calcForceExertedByX(planet);
				totalX += forceX; 
			}
			
		}
		return totalX;
	}

	public double calcNetForceExertedByY(Planet[] planets){
		double totalY = 0;
		for(Planet planet : planets){
			if(! this.equals(planet)){
				double forceY = calcForceExertedByY(planet);
				totalY += forceY;
			}
		}
		return totalY;
	}

	public void update(double dt, double fx, double fy){
		double ax = fx / this.mass;
		double ay = fy / this .mass;
		double vx = this.xxVel +  dt * ax;
		double vy = this.yyVel + dt * ay;
		this.xxPos = this.xxPos + dt * vx;
		this.yyPos = this.yyPos + dt * vy;
		this.xxVel = vx;
		this.yyVel = vy;

	}

	public void draw(){
		StdDraw.picture(xxPos/1e11,yyPos/1e11,"images/"+imgFileName);
	}


}	