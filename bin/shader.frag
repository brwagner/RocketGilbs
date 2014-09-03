#version 120

//#define iterations 10
//#define volsteps 5
//#define stepsize 0.3

varying vec3 color;
uniform vec2 resolution;
uniform float time;
uniform vec2 gPos;
uniform float gVel;
uniform float deathScale;
uniform vec2 cPos;
uniform vec2 scaler;

uniform float zoom;

uniform float pPosX[10];
uniform float pPosY[10];
uniform float pRad[10];

#define formuparam 0.73

//#define zoom   0.500
#define tile   0.450
#define speed  0.010

#define brightness 0.004
#define darkmatter 1.000
#define distfading 0.730
#define saturation 1.000

float haversine(float x) {
	return 0.5 + 0.5 * sin(x);
}

float havercosine(float x) {
	return 0.5 + 0.5 * cos(x);
}

float aura(vec2 point, vec2 origin, float falloff) {
	vec2 pmo = point - origin;
	return exp(-falloff * dot(pmo,pmo));
}

vec3 stars()
{
	// Star Nest by Pablo Román Andrioli
	// This content is under the MIT License.
	//get coords and direction
	vec2 uv=gl_FragCoord.xy/resolution.xy-.5;
	uv.y*=resolution.y/resolution.x;
	vec3 dir=vec3(uv*zoom,1.);
	float dt=time*speed+.25;

	//mouse rotation
	float a1=0.5;
	float a2=0.8;
	mat2 rot1=mat2(cos(a1),sin(a1),-sin(a1),cos(a1));
	mat2 rot2=mat2(cos(a2),sin(a2),-sin(a2),cos(a2));
	dir.xz*=rot1;
	dir.xy*=rot2;
	vec3 from=vec3(1.,.5,0.5);
	from+=vec3(dt*2.,dt,-2.);
	from.xz*=rot1;
	from.xy*=rot2;
	
	//volumetric rendering
	float s=0.1,fade=1.;
	vec3 v=vec3(0.);
	for (int r=0; r<volsteps; r++) {
		vec3 p=from+s*dir*.5;
		p = abs(vec3(tile)-mod(p,vec3(tile*2.))); // tiling fold
		float pa,a=pa=0.;
		for (int i=0; i<iterations; i++) { 
			p=abs(p)/dot(p,p)-formuparam; // the magic formula
			a+=abs(length(p)-pa); // absolute sum of average change
			pa=length(p);
		}
		float dm=max(0.,darkmatter-a*a*.001); //dark matter
		a*=a*a; // add contrast
		if (r>6) fade*=1.-dm; // dark matter, don't render near
		//v+=vec3(dm,dm*.5,0.);
		v+=fade;
		v+=vec3(s,s*s,s*s*s*s)*a*brightness*fade; // coloring based on distance
		fade*=distfading; // distance fading
		s+=stepsize;
	}
	v=mix(vec3(length(v)),v,saturation); //color adjust
	return v*0.01;
	
}

void main() {
	vec2 uv = gl_FragCoord.xy;
	uv *= scaler;
	uv.y = resolution.y - uv.y;
	
	vec3 col = color.rgb;
	
	col += stars();
	
	col += vec3(gVel/7,gVel/7,0.0) * aura(uv, gPos, 0.001);
	col += vec3(1.0,1.0,1.0) * aura(uv, gPos, deathScale);
	
	for(int i = 0; i < 10 ; i++)
	{
		if(pPosX[i] != 0 && pPosY[i] != 0)
		{
			col += vec3(0f, 0f, 2.5f) * aura(uv, vec2(pPosX[i],pPosY[i]), 0.01/pRad[i]);
		}
	}
	
	col += vec3(1.0,0.5,0.0) * aura(uv, cPos, 0.001);
	
    gl_FragColor = vec4(col,1.0);
}

// Copyright 2014 Benjamin Wagner using a GPL license