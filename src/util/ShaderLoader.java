package util;
// Copyright 2014 Benjamin Wagner using a GPL license

import org.lwjgl.opengl.Display;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;

/**
 * Loads the shaders from their respective files into OpenGL. This class reads the fragment and
 * vertex shaders line by line into a stringbuilder and then compiles it into OpenGL and writes any
 * exceptions to the console
 */
public class ShaderLoader {

  int shaderProgram; // Used to identify the program in OpenGL
  int vertexShader; // Used to identify the vertex shader
  int fragmentShader; // Used to identify the fragment shader
  StringBuilder vertexShaderSource; // Generated from shader.vert
  StringBuilder fragmentShaderSource; // Generated from shader.frag
  String fileName; // The file name of the shaders
  Settings settings; // Performance tweaks injected into source code

  // Constructor
  public ShaderLoader(String fileName, int settings) {
    this.fileName = fileName;
    this.shaderProgram = glCreateProgram();
    this.vertexShader = glCreateShader(GL_VERTEX_SHADER);
    this.fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
    this.vertexShaderSource = new StringBuilder();
    this.fragmentShaderSource = new StringBuilder();
    this.settings = new Settings(settings);

    readVertexShader(fileName);
    readFragmentShader(fileName);

    compileVertexShader();
    compileFragmentShader();
  }

  // Reads vertex shader from source into stringbuilder
  private void readVertexShader(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("src/" + fileName + ".vert"));
      String line;
      while ((line = reader.readLine()) != null) {
        vertexShaderSource.append(line).append("\n");
      }
      reader.close();
    } catch (IOException e) {
      System.err.println("vertex shader not loaded properly");
      Display.destroy();
      System.exit(1);
    }
  }

  // Reads fragment shader from source into stringbuilder
  private void readFragmentShader(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("src/" + fileName + ".frag"));
      String line;
      fragmentShaderSource.append(reader.readLine()).append("\n");
      fragmentShaderSource.append(this.settings.getSettings());
      while ((line = reader.readLine()) != null) {
        fragmentShaderSource.append(line).append("\n");
      }
      reader.close();
    } catch (IOException e) {
      System.err.println("fragment shader not loaded properly");
      Display.destroy();
      System.exit(1);
    }
  }

  @SuppressWarnings("deprecation") // Compile the vertex shader
  private void compileVertexShader() {
    glShaderSource(vertexShader, vertexShaderSource);
    glCompileShader(vertexShader);

    if (glGetShader(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
      System.err.println("Vertex shader wasn't able to be compiled correctly");
    }

    glShaderSource(fragmentShader, fragmentShaderSource);
    glCompileShader(fragmentShader);
  }

  @SuppressWarnings("deprecation") // Compile the fragment shader
  private void compileFragmentShader() {
    if (glGetShader(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
      System.err.println("Fragment shader wasn't able to be compiled correctly");
    }

    glAttachShader(shaderProgram, vertexShader);
    glAttachShader(shaderProgram, fragmentShader);

    glLinkProgram(shaderProgram);
    glValidateProgram(shaderProgram);
  }

////////////////////////////////////////////////////////////////////////////////
//                        Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public int getShaderProgram() {
    return shaderProgram;
  }

  public void setShaderProgram(int shaderProgram) {
    this.shaderProgram = shaderProgram;
  }

  public int getVertexShader() {
    return vertexShader;
  }

  public void setVertexShader(int vertexShader) {
    this.vertexShader = vertexShader;
  }

  public int getFragmentShader() {
    return fragmentShader;
  }

  public void setFragmentShader(int fragmentShader) {
    this.fragmentShader = fragmentShader;
  }

  public StringBuilder getVertexShaderSource() {
    return vertexShaderSource;
  }

  public void setVertexShaderSource(StringBuilder vertexShaderSource) {
    this.vertexShaderSource = vertexShaderSource;
  }

  public StringBuilder getFragmentShaderSource() {
    return fragmentShaderSource;
  }
}
