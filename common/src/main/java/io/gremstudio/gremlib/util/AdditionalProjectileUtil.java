package io.gremstudio.gremlib.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

import static net.minecraft.world.entity.projectile.ProjectileUtil.getEntityHitResult;

public class AdditionalProjectileUtil {
    private static final float defaultMargin = 0.3f;
    public static HitResult getHitResultOnMoveVectorInclLiquid(Entity projectile, Predicate<Entity> filter) {
        Vec3 vec3 = projectile.getDeltaMovement();
        Level level = projectile.level();
        Vec3 vec32 = projectile.position();
        return getHitResult(vec32, projectile, filter, vec3, level, defaultMargin, ClipContext.Block.COLLIDER);
    }

    public static HitResult getHitResultOnMoveVectorInclLiquid(Entity projectile, Predicate<Entity> filter, ClipContext.Block clipContext) {
        Vec3 vec3 = projectile.getDeltaMovement();
        Level level = projectile.level();
        Vec3 vec32 = projectile.position();
        return getHitResult(vec32, projectile, filter, vec3, level, defaultMargin, clipContext);
    }

    private static HitResult getHitResult(
            Vec3 pos, Entity projectile, Predicate<Entity> filter, Vec3 deltaMovement, Level level, float margin, ClipContext.Block clipContext
    ) {
        Vec3 vec3 = pos.add(deltaMovement);
        HitResult hitResult = level.clipIncludingBorder(new ClipContext(pos, vec3, clipContext, ClipContext.Fluid.SOURCE_ONLY, projectile));
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3 = hitResult.getLocation();

        }

        HitResult hitResult2 = getEntityHitResult(level, projectile, pos, vec3, projectile.getBoundingBox().expandTowards(deltaMovement).inflate(1.0), filter, margin);
        if (hitResult2 != null) {
            hitResult = hitResult2;
        }

        return hitResult;
    }
}
