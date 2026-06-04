package io.siuolplex.gremlib.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class WoodSetInfo {
    public static WoodSetInfo OVERWORLD;
    public static WoodSetInfo NETHER;
    public static WoodSetInfo BAMBOO;

    Pair<Boolean, String> logInfo;
    Pair<Boolean, String> woodInfo;
    Pair<Boolean, BoatType> boatInfo;
    boolean doMosaic;

    public WoodSetInfo(Pair<Boolean, String> logInfo, Pair<Boolean, String> woodInfo, Pair<Boolean, BoatType> boatInfo, boolean doMosaic) {
        this.logInfo = logInfo;
        this.boatInfo = boatInfo;
        this.woodInfo = woodInfo;
        this.doMosaic = doMosaic;
    }

    public Pair<Boolean, String> getLogInfo() {
        return logInfo;
    }

    public Pair<Boolean, String> getWoodInfo() {
        return woodInfo;
    }

    public Pair<Boolean, BoatType> getBoatInfo() {
        return boatInfo;
    }

    public boolean canDoMosaic() {
        return doMosaic;
    }

    public enum BoatType {
        BOAT("boat", (item, type, level) -> new Boat(type, level, item), (ChestBoatFactory) (item, type, level) -> new ChestBoat(type, level, item)),
        RAFT("raft", (item, type, level) -> new Raft(type, level, item), (ChestRaftFactory) (item, type, level) -> new ChestRaft(type, level, item));

        public final String name;

        // Need to have both factories because java is a bitch.
        public BoatFactory boatFactory;
        public ChestBoatFactory chestBoatFactory;
        public RaftFactory raftFactory;
        public ChestRaftFactory chestRaftFactory;

        BoatType(String name, BoatFactory boatFactory, ChestBoatFactory chestBoatFactory) {
            this.name = name;
            this.boatFactory = boatFactory;
            this.chestBoatFactory = chestBoatFactory;
        }
        BoatType(String name, RaftFactory raftFactory, ChestRaftFactory chestRaftFactory) {
            this.name = name;
            this.raftFactory = raftFactory;
            this.chestRaftFactory = chestRaftFactory;
        }


        public interface AbstractBoatFactory<T extends AbstractBoat> {
            T apply(Supplier<Item> itemSupplier, EntityType<T> type, Level level);
        }

        public interface BoatFactory extends AbstractBoatFactory<Boat> { }
        public interface ChestBoatFactory extends AbstractBoatFactory<ChestBoat> { }

        public interface RaftFactory extends AbstractBoatFactory<Raft> { }
        public interface ChestRaftFactory extends AbstractBoatFactory<ChestRaft> { }

    }
}
