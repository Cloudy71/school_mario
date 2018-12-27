/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 22:32
*/

package cz.cloudy.pacman.scenes;

import cz.cloudy.fxengine.core.*;
import cz.cloudy.fxengine.interfaces.IGameScene;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.objects.Coin;
import cz.cloudy.pacman.objects.PacMan;
import javafx.scene.paint.Color;

public class MenuScene
        implements IGameScene {
    private int page;

    @Override
    public void start() {
        page = 0;

        int num = 5;
        float offset = Renderer.get()
                               .getSceneSize().x / num;

        float pacManPath = Renderer.get()
                                   .getSceneSize().x + 96f;

        Coin[] coins = new Coin[num];

        TimerService.beginStack();
        TimerService.setTimer(2000, () -> {
            for (int i = 0; i < num; i++) {
                coins[i] = GameObjectFactory.createObject(Coin.class);
                coins[i].setPosition(new Vector2(offset / 2f + i * offset, Renderer.get()
                                                                                   .getSceneSize().y / 2f));
                coins[i].setScale(new Vector2(3f, 3f));

                AnimationService.beginAnimation(coins[i]);
                AnimationService.addKeyFrame(5000, KeyFrame.KeyFrameType.CUSTOM, new CustomValue("opacity", 1f));
                AnimationService.endAnimation();
            }
        }); // Spawn coins
        TimerService.setTimer(5000, () -> {
            PacMan pacMan = GameObjectFactory.createObject(PacMan.class);
            pacMan.setScale(new Vector2(3f, 3f));
            pacMan.setPosition(new Vector2(-48f, Renderer.get()
                                                         .getSceneSize().y / 2f));

            AnimationService.beginAnimation(pacMan);
            AnimationService.addKeyFrame(5000, KeyFrame.KeyFrameType.MOVE,
                                         new NormalValue(new Vector2(pacManPath, pacMan.getPosition().y)));
            AnimationService.endAnimation();
        });
        TimerService.endStack();
        for (int i = 0; i < num; i++) {
            final int ii = i;
            TimerService.setTimer(7000 + (int) ((16f + offset / 2f + ii * offset) * 5000 / pacManPath), () -> {
                coins[ii].destroy();
            });
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Render.begin()
              .color()
              .paint(Color.BLACK)
              .end()
              .clear(true)
              .finish();
    }
}
