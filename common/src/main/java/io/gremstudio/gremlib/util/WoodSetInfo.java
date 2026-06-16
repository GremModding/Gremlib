package io.gremstudio.gremlib.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class WoodSetInfo {
    public static WoodSetInfo OVERWORLD = new WoodSetInfo(
            new Pair<>(true, "log"),
            new Pair<>(true, "wood"),
            new Pair<>(true, BoatType.BOAT),
            false,
            true,
            "standard"
    );

    public static WoodSetInfo NETHER = new WoodSetInfo(
            new Pair<>(true, "stem"),
            new Pair<>(true, "hyphae"),
            new Pair<>(false, null),
            false,
            false,
            "nether"
    );

    public static WoodSetInfo BAMBOO = new WoodSetInfo(
            new Pair<>(true, "block"),
            new Pair<>(false, null),
            new Pair<>(true, BoatType.RAFT),
            true,
            true,
            "bamboo"
    );

    Pair<Boolean, String> logInfo;
    Pair<Boolean, String> woodInfo;
    Pair<Boolean, BoatType> boatInfo;
    boolean doMosaic;
    boolean canBurn;
    String name;

    public WoodSetInfo(Pair<Boolean, String> logInfo,
                       Pair<Boolean, String> woodInfo,
                       Pair<Boolean, BoatType> boatInfo,
                       boolean doMosaic, boolean canBurn, String name) {
        this.logInfo = logInfo;
        this.boatInfo = boatInfo;
        this.woodInfo = woodInfo;
        this.doMosaic = doMosaic;
        this.canBurn = canBurn;
        this.name = name;
    }

    public boolean hasLogs() {
        return logInfo.getFirst();
    }

    public String getLogs() {
        return logInfo.getSecond();
    }


    public boolean hasWoods() {
        return woodInfo.getFirst();
    }

    public String getWoods() {
        return woodInfo.getSecond();
    }


    public boolean hasBoat() {
        return boatInfo.getFirst();
    }

    public BoatType getBoat() {
        return boatInfo.getSecond();
    }

    public boolean canDoMosaic() {
        return doMosaic;
    }

    public boolean canBurn() {
        return canBurn;
    }

    public String getName() {
        return name;
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
