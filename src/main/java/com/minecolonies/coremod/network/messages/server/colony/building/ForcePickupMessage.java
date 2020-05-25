package com.minecolonies.coremod.network.messages.server.colony.building;

import com.ldtteam.structurize.util.LanguageHandler;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.buildings.IBuilding;
import com.minecolonies.api.colony.buildings.views.IBuildingView;
import com.minecolonies.coremod.network.messages.server.AbstractBuildingServerMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import static com.minecolonies.api.colony.requestsystem.requestable.deliveryman.AbstractDeliverymanRequestable.MAX_DELIVERYMAN_PLAYER_PRIORITY;
import static com.minecolonies.api.util.constant.TranslationConstants.COM_MINECOLONIES_COREMOD_ENTITY_DELIVERYMAN_FORCEPICKUP_FAILED;

public class ForcePickupMessage extends AbstractBuildingServerMessage<IBuilding>
{
    /**
     * Empty public constructor.
     */
    public ForcePickupMessage()
    {
        super();
    }

    /**
     * Creates message for player to force a pickup.
     *
     * @param building view of the building to read data from
     */
    public ForcePickupMessage(@NotNull final IBuildingView building)
    {
        super(building);
    }

    /**
     * Transformation from a byteStream to the variables.
     *
     * @param buf the used byteBuffer.
     */
    @Override
    public void fromBytesOverride(@NotNull final PacketBuffer buf)
    {
        // Noop
    }

    /**
     * Transformation to a byteStream.
     *
     * @param buf the used byteBuffer.
     */
    @Override
    public void toBytesOverride(@NotNull final PacketBuffer buf)
    {
        // Noop
    }

    @Override
    protected void onExecute(final NetworkEvent.Context ctxIn, final boolean isLogicalServer, final IColony colony, final IBuilding building)
    {
        if (building.createPickupRequest(MAX_DELIVERYMAN_PLAYER_PRIORITY))
        {
            building.markDirty();
        }
        else
        {
            final PlayerEntity player = ctxIn.getSender();
            if (player == null)
            {
                return;
            }

            LanguageHandler.sendPlayerMessage(player, COM_MINECOLONIES_COREMOD_ENTITY_DELIVERYMAN_FORCEPICKUP_FAILED);
        }
    }
}
