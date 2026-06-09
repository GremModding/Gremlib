package io.gremstudio.gremlib.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class VectorUtil {
    public static Vec3i toVec3i(Vec3 vec3) {
        return new Vec3i((int) (vec3.x+0.5), (int) (vec3.y+0.5), (int) (vec3.z+0.5));
    }

    public static Vec3 fromVec3ftoVec3(Vector3f vec3f) {
        return new Vec3(vec3f.x,vec3f.y,vec3f.z);
    }

    public static BlockPos fromVec3ftoBlockpos(Vector3f vec3f) {
        return new BlockPos((int) (vec3f.x+0.5f), (int) (vec3f.y+0.5f), (int) (vec3f.z+0.5f));
    }

    /**
     Scales a Vec3's total to a given value.
     Naming is hard with this.
     @param vec3 Input vector for this calculation
     @param totalLength The length that will be scaled to
     @return A Vec3 Vector that is scaled to the total length.
     */
    public static Vec3 scaleVec3To(Vec3 vec3, double totalLength) {
        double average = vec3.length()/9; // The division by 9 is two divisions of 3 in this case, one to average and another to make the total of this 1.
        double x = vec3.x * 1/average;
        double y = vec3.y * 1/average;
        double z = vec3.z * 1/average;
        return new Vec3(x, y, z).scale(totalLength);
    }

    public static Vec3 distanceBetween(Vec3 from, Vec3 to) {
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double dz = to.z - from.z;
        return new Vec3(dx, dy, dz);
    }

    public static Vec3 averageVec3WithWeight(Vec3 vec3a, Vec3 vec3b, int weight) {
        double x = (vec3a.x * weight + vec3b.x)/(weight + 1);
        double y = (vec3a.y * weight + vec3b.y)/(weight + 1);
        double z = (vec3a.z * weight + vec3b.z)/(weight + 1);
        return new Vec3(x, y, z);
    }
}
