package de.htwg.se.uno.util

trait Command {
  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit
}
