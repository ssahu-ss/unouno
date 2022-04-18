package de.htwg.se.uno.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.uno.util.Command
import scala.util.{Failure, Success}
import de.htwg.se.uno.controller.controllerComponent.GameChanged

class PullCommand(controller: Controller) extends Command:
  override def doStep(): Unit =
    controller.game = controller.game.pullMove()

  override def undoStep(): Unit = 
    controller.redoList = controller.fileIo.gameToString(controller.game) :: controller.redoList
    val result = controller.fileIo.load(controller.undoList.head)
    result match
      case Success(value) => 
        controller.game = value
        controller.undoList = controller.undoList.tail
      case Failure(e) =>
        controller.redoList = controller.redoList.tail
        controller.controllerEvent("couldNotUndo")
        controller.publish(new GameChanged)

  override def redoStep(): Unit =
    controller.undoList = controller.fileIo.gameToString(controller.game) :: controller.undoList
    val result = controller.fileIo.load(controller.redoList.head)
    result match
      case Success(value) => 
        controller.game = value
        controller.redoList = controller.redoList.tail
      case Failure(e) =>
        controller.undoList = controller.undoList.tail
        controller.controllerEvent("couldNotRedo")
        controller.publish(new GameChanged)