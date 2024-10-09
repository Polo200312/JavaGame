package ecs.Systems;

import ecs.Entities.Entity;
import edu.usu.graphics.Color;
import edu.usu.graphics.Font;
import edu.usu.graphics.Graphics2D;
import utils.Misc;

public class HUDRenderer extends System {

    private final Graphics2D graphics;
    private Font fontStatus;
    private Font fontGameOver;
    private Entity lander;

    public HUDRenderer(Graphics2D graphics) {
        super(ecs.Components.Lander.class);

        this.graphics = graphics;

        fontStatus = new Font("resources/fonts/CourierPrime-Regular.ttf", 24, false);
        fontGameOver = new Font("resources/fonts/Roboto-Regular.ttf", 68, true);
        fontStatus = new Font("resources/fonts/Roboto-Regular.ttf", 68, true);
    }

    /**
     * Look only for the lander entity
     */
    @Override
    public boolean add(Entity entity) {
        boolean added = isInterested(entity);
        if (added) {
            this.lander = entity;
        }
        return added;
    }

    @Override
    public void update(double elapsedTime) {
        renderStatus();
        renderMessage();
    }

    private void renderStatus() {
        if (lander != null) {
            var status = lander.get(ecs.Components.LanderStatus.class);
            var params = lander.get(ecs.Components.LanderParams.class);

            String message1 = String.format("Fuel  :  %.2f  s", status.fuel);
            String message2 = String.format("Speed  :  %.2f  m/s",status.getSpeed());
            String message3 = String.format("Angle  :  %.2f  °", Math.toDegrees(status.rotation) % Math.toDegrees(2 * Math.PI));
            float height = 0.02f;
            float width = fontStatus.measureTextWidth(message1, height);
            float left = 0 + width / 2 + 0.1f;
            graphics.drawTextByHeight(fontStatus, message1, left, -0.40f, height, Misc.LAYER_HUD, status.fuel >= 10.00f ? Color.WHITE : Color.RED);
            graphics.drawTextByHeight(fontStatus, message2, left, -0.35f, height, Misc.LAYER_HUD, status.getSpeed() < params.safeSpeed ? Color.GREEN : Color.RED);
            graphics.drawTextByHeight(fontStatus, message3, left, -0.30f, height, Misc.LAYER_HUD, ((float) Math.toRadians(Math.toDegrees(status.rotation) % Math.toDegrees(2 * Math.PI)) < params.safeAngle || ((float) Math.toRadians(Math.toDegrees(2 * Math.PI) - Math.toDegrees(status.rotation)) < params.safeAngle && (float) Math.toRadians(Math.toDegrees(2 * Math.PI) - Math.toDegrees(status.rotation)) > 0)) ? Color.GREEN : Color.RED);
            // TODO: Render the 'fuel', 'speed', and 'angle' status
        }
    }

    private void renderMessage() {
        if (lander != null) {
            var status = lander.get(ecs.Components.LanderStatus.class);

            // TODO: Change these game over messages
            if (status.landed) {
                final String message = "...Ranni has landed , congratulations! ...";
                float height = 0.08f;
                float width = fontGameOver.measureTextWidth(message, height);
                float left = 0 - width / 2;
                graphics.drawTextByHeight(fontGameOver, message, left, -0.25f, height, Misc.LAYER_HUD, Color.WHITE);
            }
            if (!status.alive) {
                final String message = "...My promised king, please try again...";//杂魚杂魚❤ ,
                float height = 0.08f;
                float width = fontGameOver.measureTextWidth(message, height);
                float left = 0 - width / 2;
                graphics.drawTextByHeight(fontGameOver, message, left, -0.25f, height, Misc.LAYER_HUD, Color.WHITE);
            }
        }
    }
}
