package me.fizzify.aquariusclient.mixins.client.gui;

import me.fizzify.aquariusclient.Client;
import me.fizzify.aquariusclient.event.impl.TickEvent;
import me.fizzify.aquariusclient.module.HUDConfigScreen;
import me.fizzify.aquariusclient.module.ModuleManager;
import me.fizzify.aquariusclient.ui.clickgui.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui
{

    @Shadow @Final
    private Minecraft mc;
    @Shadow
    private long healthUpdateCounter;
    @Shadow
    private int updateCounter;
    @Shadow
    private int playerHealth;
    @Shadow
    private long lastSystemTime;
    @Shadow
    private int lastPlayerHealth;
    @Shadow @Final
    private Random rand;

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    private void runTick (CallbackInfo info) {
        if(!(mc.currentScreen instanceof HUDConfigScreen) && !(mc.currentScreen instanceof ClickGUI)) {
            Client.INSTANCE.moduleManager.renderModules();
        }
    }

    /**
     * @author asbyth
     * @reason health flashing
     */
    @Overwrite
    protected void renderPlayerStats(ScaledResolution resolution) {
        if (mc.getRenderViewEntity() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) mc.getRenderViewEntity();
            int currentHealth = MathHelper.ceiling_float_int(entityplayer.getHealth());
            boolean playerIsTakingDamage = healthUpdateCounter > (long) updateCounter && (healthUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

            if (currentHealth < playerHealth && entityplayer.hurtResistantTime > 0) {
                lastSystemTime = Minecraft.getSystemTime();
                healthUpdateCounter = (long) (updateCounter + 20);
            } else if (currentHealth > playerHealth && entityplayer.hurtResistantTime > 0) {
                lastSystemTime = Minecraft.getSystemTime();
                healthUpdateCounter = (long) (updateCounter + 10);
            }

            if (Minecraft.getSystemTime() - lastSystemTime > 1000L) {
                playerHealth = currentHealth;
                lastPlayerHealth = currentHealth;
                lastSystemTime = Minecraft.getSystemTime();
            }

            playerHealth = currentHealth;
            int lastPlayerHealth = this.lastPlayerHealth;
            rand.setSeed((long) (updateCounter * 312871));

            boolean bool = false;
            FoodStats foodstats = entityplayer.getFoodStats();
            int foodLevel = foodstats.getFoodLevel();
            int prevFoodLevel = foodstats.getPrevFoodLevel();

            int left = resolution.getScaledWidth() / 2 - 91;
            int right = resolution.getScaledWidth() / 2 + 91;
            int height = resolution.getScaledHeight() - 39;

            IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            float attributeValue = (float) iattributeinstance.getAttributeValue();
            float absorptionAmount = entityplayer.getAbsorptionAmount();
            int extraHealth = MathHelper.ceiling_float_int((attributeValue + absorptionAmount) / 2.0F / 10.0F);
            int extraHeart = Math.max(10 - (extraHealth - 2), 3);
            int heartHeight = height - (extraHealth - 1) * extraHeart - 10;
            float tempAbsorptionAmount = absorptionAmount;
            int regeneration = -1;

            if (entityplayer.isPotionActive(Potion.regeneration)) {
                regeneration = updateCounter % MathHelper.ceiling_float_int(attributeValue + 5.0F);
            }

            int armorValue = entityplayer.getTotalArmorValue();
            mc.mcProfiler.startSection("armor");

            for (int armorPosition = 0; armorPosition < 10; ++armorPosition) {
                if (armorValue > 0) {
                    int j3 = left + armorPosition * 8;

                    if (armorPosition * 2 + 1 < armorValue) {
                        drawTexturedModalRect(j3, heartHeight, 34, 9, 9, 9);
                    }

                    if (armorPosition * 2 + 1 == armorValue) {
                        drawTexturedModalRect(j3, heartHeight, 25, 9, 9, 9);
                    }

                    if (armorPosition * 2 + 1 > armorValue) {
                        drawTexturedModalRect(j3, heartHeight, 16, 9, 9, 9);
                    }
                }
            }

            mc.mcProfiler.endStartSection("health");

            for (int healthHeartAmount = MathHelper.ceiling_float_int((attributeValue + absorptionAmount) / 2.0F) - 1; healthHeartAmount >= 0; --healthHeartAmount) {
                int originalTextureX = 16;

                if (entityplayer.isPotionActive(Potion.poison)) {
                    originalTextureX += 36;
                } else if (entityplayer.isPotionActive(Potion.wither)) {
                    originalTextureX += 72;
                }

                int takingDamageState = 0;
                if (playerIsTakingDamage) {
                    takingDamageState = 1;
                }

                int healthInt = MathHelper.ceiling_float_int((float) (healthHeartAmount + 1) / 10.0F) - 1;
                int healthWidth = left + healthHeartAmount % 10 * 8;
                int healthHeight = height - healthInt * extraHeart;

                if (currentHealth <= 4) {
                    healthHeight += rand.nextInt(2);
                }

                if (healthHeartAmount == regeneration) {
                    healthHeight -= 2;
                }

                int difficultyState = 0;
                if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
                    difficultyState = 5;
                }

                drawTexturedModalRect(healthWidth, healthHeight, 16 + takingDamageState * 9, 9 * difficultyState, 9, 9);

                if (ModuleManager.oldAnimations.isDisabled()) {
                    if (playerIsTakingDamage) {
                        if (healthHeartAmount * 2 + 1 < lastPlayerHealth) {
                            drawTexturedModalRect(healthWidth, healthHeight, originalTextureX + 54, 9 * difficultyState, 9, 9);
                        }

                        if (healthHeartAmount * 2 + 1 == lastPlayerHealth) {
                            drawTexturedModalRect(healthWidth, healthHeight, originalTextureX + 63, 9 * difficultyState, 9, 9);
                        }
                    }
                }

                if (tempAbsorptionAmount > 0.0F) {

                    if (tempAbsorptionAmount == absorptionAmount && absorptionAmount % 2.0F == 1.0F) {
                        drawTexturedModalRect(healthWidth, healthHeight, originalTextureX + 153, 9 * difficultyState, 9, 9);
                    } else {
                        drawTexturedModalRect(healthWidth, healthHeight, originalTextureX + 144, 9 * difficultyState, 9, 9);
                    }

                    tempAbsorptionAmount -= 2.0F;
                } else {
                    if (healthHeartAmount * 2 + 1 < currentHealth) {
                        drawTexturedModalRect(healthWidth, healthHeight, originalTextureX + 36, 9 * difficultyState, 9, 9);
                    }

                    if (healthHeartAmount * 2 + 1 == currentHealth) {
                        drawTexturedModalRect(healthWidth, healthHeight, originalTextureX + 45, 9 * difficultyState, 9, 9);
                    }
                }
            }
            Entity entity = entityplayer.ridingEntity;

            if (entity == null) {
                mc.mcProfiler.endStartSection("food");

                for (int foodPostion = 0; foodPostion < 10; ++foodPostion) {
                    int foodHeight = height;
                    int textureXT = 16;
                    int textureX = 0;

                    if (entityplayer.isPotionActive(Potion.hunger)) {
                        textureXT += 36;
                        textureX = 13;
                    }

                    if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && updateCounter % (foodLevel * 3 + 1) == 0) {
                        foodHeight = height + (rand.nextInt(3) - 1);
                    }

                    if (bool) {
                        textureX = 1;
                    }

                    int foodPositionX = right - foodPostion * 8 - 9;

                    drawTexturedModalRect(foodPositionX, foodHeight, 16 + textureX * 9, 27, 9, 9);
                    if (bool) {
                        if (foodPostion * 2 + 1 < prevFoodLevel) {
                            drawTexturedModalRect(foodPositionX, foodHeight, textureXT + 54, 27, 9, 9);
                        }

                        if (foodPostion * 2 + 1 == prevFoodLevel) {
                            drawTexturedModalRect(foodPositionX, foodHeight, textureXT + 63, 27, 9, 9);
                        }
                    }

                    if (foodPostion * 2 + 1 < foodLevel) {
                        drawTexturedModalRect(foodPositionX, foodHeight, textureXT + 36, 27, 9, 9);
                    }

                    if (foodPostion * 2 + 1 == foodLevel) {
                        drawTexturedModalRect(foodPositionX, foodHeight, textureXT + 45, 27, 9, 9);
                    }
                }
            } else if (entity instanceof EntityLivingBase) {
                mc.mcProfiler.endStartSection("mountHealth");
                EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

                int tempHealth = (int) Math.ceil((double) entitylivingbase.getHealth());
                float maxHealth = entitylivingbase.getMaxHealth();
                int maxHeart = (int) (maxHealth + 0.5F) / 2;

                if (maxHeart > 30) {
                    maxHeart = 30;
                }

                int mountHealthHeight = height;

                for (int tempInt = 0; maxHeart > 0; tempInt += 20) {
                    int heartInt = Math.min(maxHeart, 10);
                    maxHeart -= heartInt;

                    for (int mountHealth = 0; mountHealth < heartInt; ++mountHealth) {
                        int textureX = 52;
                        int additionalX = 0;

                        if (bool) {
                            additionalX = 1;
                        }

                        int mountHealthPositionX = right - mountHealth * 8 - 9;
                        drawTexturedModalRect(mountHealthPositionX, mountHealthHeight, textureX + additionalX * 9, 9, 9, 9);

                        if (mountHealth * 2 + 1 + tempInt < tempHealth) {
                            drawTexturedModalRect(mountHealthPositionX, mountHealthHeight, textureX + 36, 9, 9, 9);
                        }

                        if (mountHealth * 2 + 1 + tempInt == tempHealth) {
                            drawTexturedModalRect(mountHealthPositionX, mountHealthHeight, textureX + 45, 9, 9, 9);
                        }
                    }

                    mountHealthHeight -= 10;
                }
            }

            mc.mcProfiler.endStartSection("air");

            if (entityplayer.isInsideOfMaterial(Material.water)) {
                int air = mc.thePlayer.getAir();
                int airCheck = MathHelper.ceiling_double_int((double) (air - 2) * 10.0D / 300.0D);
                int air2 = MathHelper.ceiling_double_int((double) air * 10.0D / 300.0D) - airCheck;

                for (int airPosition = 0; airPosition < airCheck + air2; ++airPosition) {
                    if (airPosition < airCheck) {
                        drawTexturedModalRect(right - airPosition * 8 - 9, heartHeight, 16, 18, 9, 9);
                    } else {
                        drawTexturedModalRect(right - airPosition * 8 - 9, heartHeight, 25, 18, 9, 9);
                    }
                }
            }

            mc.mcProfiler.endSection();
        }
    }
}
