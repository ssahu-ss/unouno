package de.htwg.se.uno.util

import de.htwg.se.uno.controller.controllerComponent.controllerBaseImpl.{Controller, PullCommand}
import de.htwg.se.uno.model.gameComponent.gameBaseImpl.Game
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class UndoManagerSpec extends AnyWordSpec {
  "A UndoManager" when {
    "new" should {
      val undoManager = new UndoManager()
      var controller = new Controller(Game(4))
      controller.createGame(4)
      var command = new PullCommand(controller)
      "Not be able to undo a Step" in {
        undoManager.undoStep()
        controller.game.activePlayer should be(3)
      }
      "Not be able to redo a Step" in {
        undoManager.redoStep()
        controller.game.activePlayer should be(3)
      }
      "be able to do a Step" in {
        undoManager.doStep(command)
        controller.game.player.handCards.length should be(10)
      }
      "be able to undo a Step" in {
        undoManager.undoStep()
        controller.game.player.handCards.length should be(9)
      }
      "be able to redo a Step" in {
        undoManager.redoStep()
        controller.game.player.handCards.length should be(10)
      }
    }
  }
}
