/*
  User: Cloudy
  Date: 25-Dec-18
  Time: 22:32
*/

package cz.cloudy.pacman.scenes;

import cz.cloudy.fxengine.core.GameObjectFactory;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.core.TimerService;
import cz.cloudy.fxengine.interfaces.IGameScene;
import cz.cloudy.fxengine.types.Vector2;
import cz.cloudy.pacman.objects.Coin;

public class MenuScene
        implements IGameScene {
    private int page;

    @Override
    public void start() {
        page = 0;

        TimerService.beginStack();
        TimerService.setTimer(2000, () -> {
            int num = 5;
            float offset = Renderer.get()
                                   .getSceneSize().x / num;
            for (int i = 0; i < num; i++) {
                Coin coin = GameObjectFactory.createObject(Coin.class);
                coin.setPosition(new Vector2(offset / 2f + i * offset, Renderer.get()
                                                                               .getSceneSize().y / 2f));
            }
        }); // Spawn coins
        TimerService.endStack();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
