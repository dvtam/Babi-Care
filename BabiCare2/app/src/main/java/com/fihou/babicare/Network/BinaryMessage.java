/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fihou.babicare.Network;

import com.fihou.babicare.Data.JSONUtils;
import com.fihou.babicare.config.Config;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import allinone.network.data.bin.BINAcceptDrawRequest;
import allinone.network.data.bin.BINActionDoiThuongRequest;
import allinone.network.data.bin.BINAddFriendByNameRequest;
import allinone.network.data.bin.BINAddFriendRequest;
import allinone.network.data.bin.BINAnPhomRequest;
import allinone.network.data.bin.BINBetMiniPokerRequest;
import allinone.network.data.bin.BINBetSlotMachineRequest;
import allinone.network.data.bin.BINBocPhomRequest;
import allinone.network.data.bin.BINBuyAvatarRequest;
import allinone.network.data.bin.BINBuyLevelRequest;
import allinone.network.data.bin.BINBuyXuRequest;
import allinone.network.data.bin.BINCancelRequest;
import allinone.network.data.bin.BINChargeAppStoreRequest;
import allinone.network.data.bin.BINChargeGiftCodeRequest;
import allinone.network.data.bin.BINChargingRequest;
import allinone.network.data.bin.BINChatPrivateRequest;
import allinone.network.data.bin.BINChatRequest;
import allinone.network.data.bin.BINChessPiece;
import allinone.network.data.bin.BINClanRequest;
import allinone.network.data.bin.BINDoiThuongRequest;
import allinone.network.data.bin.BINDrawRequest;
import allinone.network.data.bin.BINEnterZoneRequest;
import allinone.network.data.bin.BINGameConfigRequest;
import allinone.network.data.bin.BINGetAnnouncementRequest;
import allinone.network.data.bin.BINGetAvatarListRequest;
import allinone.network.data.bin.BINGetFriendListRequest;
import allinone.network.data.bin.BINGetGameHistoryRequest;
import allinone.network.data.bin.BINGetHelpRequest;
import allinone.network.data.bin.BINGetMiniPokerTopHistoryRequest;
import allinone.network.data.bin.BINGetPostListRequest;
import allinone.network.data.bin.BINGetQuyThuongMiniGameRequest;
import allinone.network.data.bin.BINGetServerDataRequest;
import allinone.network.data.bin.BINGetSlotMachineHistoryRequest;
import allinone.network.data.bin.BINGetSlotMachineTopRequest;
import allinone.network.data.bin.BINGetTableListRequest;
import allinone.network.data.bin.BINGetUserDataRequest;
import allinone.network.data.bin.BINGetUserInfoRequest;
import allinone.network.data.bin.BINGuiPhomRequest;
import allinone.network.data.bin.BINHaPhomRequest;
import allinone.network.data.bin.BINInviteRequest;
import allinone.network.data.bin.BINJoinRequest;
import allinone.network.data.bin.BINKickOutRequest;
import allinone.network.data.bin.BINLoadAppStoreItemRequest;
import allinone.network.data.bin.BINLoadConfigurationRequest;
import allinone.network.data.bin.BINLoginRequest;
import allinone.network.data.bin.BINLostRequest;
import allinone.network.data.bin.BINMiniGameHistoryRequest;
import allinone.network.data.bin.BINMiniGameTopRankRequest;
import allinone.network.data.bin.BINOfflineMessageRequest;
import allinone.network.data.bin.BINPingRequest;
import allinone.network.data.bin.BINPostCommentRequest;
import allinone.network.data.bin.BINPostDetailRequest;
import allinone.network.data.bin.BINPostNewRequest;
import allinone.network.data.bin.BINReadyRequest;
import allinone.network.data.bin.BINRegisterRequest;
import allinone.network.data.bin.BINRemoveFriendRequest;
import allinone.network.data.bin.BINReplyRequest;
import allinone.network.data.bin.BINStartRequest;
import allinone.network.data.bin.BINTaiXiuBetRequest;
import allinone.network.data.bin.BINTaiXiuGetRequest;
import allinone.network.data.bin.BINTopRequest;
import allinone.network.data.bin.BINTransferCashRequest;
import allinone.network.data.bin.BINTurnRequest;
import allinone.network.data.bin.BINUpdateInfoRequest;

public class BinaryMessage {

    public static byte[] processReq(JSONObject js) throws IOException {
        ByteArrayOutputStream bin = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bin);
        try {
            int messageID = JSONUtils.getInt(js, MessageKey.mid, 0);
            byte[] data;
//            switch (messageID) {
//                case MessagesID.LOGIN:
//                    String username = JSONUtils.getString(js, MessageKey.username, "");
//                    String password = JSONUtils.getString(js, MessageKey.password, "");
//                    String version = JSONUtils.getString(js, MessageKey.mobileVersion, "");
//                    String deviceInfo = JSONUtils.getString(js, MessageKey.deviceInfo, "");
//                    String referrer = JSONUtils.getString(js, MessageKey.referrer, "");
//                    int channel = JSONUtils.getInt(js, MessageKey.oChanel, 0);
//                    if (js.has(MessageKey.openId)) {
//                        username = JSONUtils.getString(js, MessageKey.openId, "");
//                    }
//
//                    final BINLoginRequest loginRequest = BINLoginRequest.newBuilder()
//                            .setUsername(username).setPassword(password).setCp(Config.CP)
//                            .setVersion(version).setChannel(channel)
//                            .setClientType(Config.androidType).setDeviceInfo(deviceInfo)
//                            .setReferrer(referrer)
//                            .build();
//                    data = loginRequest.toByteArray();
//                    out.writeShort(MessagesID.LOGIN);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.LOAD_CONFIG:
//                    String cp = JSONUtils.getString(js, MessageKey.cp, Config.CP);
//                    int clientType = JSONUtils.getInt(js, MessageKey.clientType, Config.androidType);
//                    int configVer = JSONUtils.getInt(js, MessageKey.configVer, 0);
//
//                    final BINLoadConfigurationRequest loadConfigRequest = BINLoadConfigurationRequest.newBuilder().setClientType(clientType).setConfigVersion(configVer).setCp(cp).build();
//                    data = loadConfigRequest.toByteArray();
//                    out.writeShort(MessagesID.LOAD_CONFIG);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.REGISTER_ACCOUNT:
//                    String mUsername = JSONUtils.getString(js, MessageKey.username, "");
//                    String mPassword = JSONUtils.getString(js, MessageKey.password, "");
//                    String mMail = JSONUtils.getString(js, MessageKey.mail, "");
//                    String mPhone = JSONUtils.getString(js, MessageKey.phone, "");
//                    boolean mMale = JSONUtils.getBoolean(js, MessageKey.male, false);
//                    int age = JSONUtils.getInt(js, MessageKey.age, 0);
//                    int mClientType = Config.androidType;
//                    String mreferrer = JSONUtils.getString(js, MessageKey.referrer, "");
//
//                    final BINRegisterRequest mBINRegisterRequest = BINRegisterRequest.newBuilder().setCp(Config.CP).setUserName(mUsername).setPassWord(mPassword).setClientType(mClientType).setGender(mMale).setAge(age).setEmail(mMail).setPhoneNumber(mPhone).setReferrer(mreferrer).build();
//                    data = mBINRegisterRequest.toByteArray();
//                    out.writeShort(MessagesID.REGISTER_ACCOUNT);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.ENTER_ZONE:
//                    int zone = JSONUtils.getInt(js, MessageKey.zone, 0);
//
//                    final BINEnterZoneRequest mBINEnterZoneRequest = BINEnterZoneRequest.newBuilder().setZoneId(zone).build();
//                    data = mBINEnterZoneRequest.toByteArray();
//                    out.writeShort(MessagesID.ENTER_ZONE);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_WAITING_LIST:
//                    int channelId = JSONUtils.getInt(js, MessageKey.channelId, 0);
//                    final BINGetTableListRequest mBINGetTableListRequest = BINGetTableListRequest.newBuilder().setChannelId(channelId).build();
//                    data = mBINGetTableListRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_WAITING_LIST);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_JOIN:
//                    int uid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    int matchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    int roomPos = JSONUtils.getInt(js, MessageKey.roomPosition, 0);
//                    boolean quickJoin = JSONUtils.getBoolean(js, MessageKey.quickplay, false);
//                    int channelIdJoin = JSONUtils.getInt(js, MessageKey.channelId, 0);
//                    final BINJoinRequest mBINJoinRequest = BINJoinRequest.newBuilder().setMatchId(matchID).setPos(roomPos).setUid(uid).setQuickJoin(quickJoin).setChannelId(channelIdJoin).build();
//                    data = mBINJoinRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_JOIN);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_CANCEL:
//                    int cuid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    int cmatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//
//                    final BINCancelRequest mBINCancelRequest = BINCancelRequest.newBuilder().setMatchID(cmatchID).setUid(cuid).build();
//                    data = mBINCancelRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_CANCEL);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_READY:
//                    int ruid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    int rmatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    final BINReadyRequest mBINReadyRequest = BINReadyRequest.newBuilder().setMatchID(rmatchID).setUid(ruid).build();
//                    data = mBINReadyRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_READY);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_START:
//                    int smatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    final BINStartRequest mBINStartRequest = BINStartRequest.newBuilder().setMatchId(smatchID).build();
//                    data = mBINStartRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_START);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_RESTART:
//                    int rematchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    final BINStartRequest reBINStartRequest = BINStartRequest.newBuilder().setMatchId(rematchID).build();
//                    data = reBINStartRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_RESTART);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_TURN:
//                    int tmatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    int zoneId = Save.gameId;
//                    BINTurnRequest mBINTurnRequest = null;
//                    switch (zoneId) {
//                        case GameId.VID_ZONE_POKER:
//                        case GameId.VID_ZONE_LIENG:
//                        case GameId.ID_ZONE_POKER:
//                        case GameId.ID_ZONE_LIENG:
//                            int moneyPoker = JSONUtils.getInt(js, MessageKey.money, 0);
//                            boolean isFold = JSONUtils.getBoolean(js, MessageKey.isFold, false);
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setBetMoney(moneyPoker).setIsGiveUp(isFold).build();
//                            break;
//                        case GameId.VID_ZONE_XITO:
//                        case GameId.ID_ZONE_XITO:
//                            int moneyXito = JSONUtils.getInt(js, MessageKey.money, 0);
//                            String cards = JSONUtils.getString(js, MessageKey.card, null);
//                            ;
//                            boolean isFoldXito = JSONUtils.getBoolean(js, MessageKey.isFold, false);
//                            boolean isShow = JSONUtils.getBoolean(js, MessageKey.isShow, false);
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setBetMoney(moneyXito).setIsGiveUp(isFoldXito).setCard(cards).setIsShow(isShow).build();
//                            break;
//                        case GameId.VID_ZONE_TIENLEN:
//                        case GameId.VID_ZONE_TIENLEN_DEM_LA:
//                        case GameId.VID_ZONE_TIENLEN_MIEN_BAC:
//                        case GameId.ID_ZONE_TIENLEN:
//                        case GameId.ID_ZONE_TIENLEN_DEM_LA:
//                        case GameId.ID_ZONE_TIENLEN_MIEN_BAC:
//                            String cardTL = JSONUtils.getString(js, MessageKey.cards, null);
//                            boolean giveup = JSONUtils.getBoolean(js, MessageKey.isGiveup, false);
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setCard(cardTL).setIsGiveUp(giveup).build();
//                            break;
//                        case GameId.VID_ZONE_BAISAM:
//                        case GameId.ID_ZONE_BAISAM:
//                            boolean samCall = JSONUtils.getBoolean(js, MessageKey.samCall, false);
//                            boolean giveupSam = JSONUtils.getBoolean(js, MessageKey.isGiveup, false);
//                            String cardSam = JSONUtils.getString(js, MessageKey.cards, "");
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setCard(cardSam).setIsGiveUp(giveupSam).setIsSamCall(samCall).build();
//                            break;
//                        case GameId.ID_ZONE_MAUBINH:
//                            String chi1 = JSONUtils.getString(js, MessageKey.chi1, "");
//                            String chi2 = JSONUtils.getString(js, MessageKey.chi2, "");
//                            String chi3 = JSONUtils.getString(js, MessageKey.chi3, "");
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setChi1(chi1).setChi2(chi2).setChi3(chi3).build();
//                            break;
//                        case GameId.ID_ZONE_COTUONG:
//                            int fromCol = JSONUtils.getInt(js, MessageKey.fromCol, 0);
//                            int fromRow = JSONUtils.getInt(js, MessageKey.fromRow, 0);
//                            int toCol = JSONUtils.getInt(js, MessageKey.toCol, 0);
//                            int toRow = JSONUtils.getInt(js, MessageKey.toRow, 0);
//                            BINChessPiece.Builder pieceTmp = BINChessPiece.newBuilder();
//                            pieceTmp.setFrCol(fromCol);
//                            pieceTmp.setFrRow(fromRow);
//                            pieceTmp.setToCol(toCol);
//                            pieceTmp.setToRow(toRow);
//                            BINChessPiece piece = pieceTmp.build();
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setChessPiece(piece).build();
//                            break;
//                        case GameId.VID_ZONE_PHOM:
//                        case GameId.ID_ZONE_PHOM:
//                            String cardphom = JSONUtils.getString(js, MessageKey.card, "");
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setCard(cardphom).build();
//                            break;
//                        case GameId.ID_ZONE_BAUCUA:
//                            int pieceBaucua = JSONUtils.getInt(js, MessageKey.piece, 0);
//                            mBINTurnRequest = BINTurnRequest.newBuilder().setMatchId(tmatchID).setCard(pieceBaucua + "").build();
//                            break;
//
//                    }
//                    data = mBINTurnRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_TURN);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GUI_PHOM:
//                    int gmatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    String gcards = JSONUtils.getString(js, MessageKey.cards, "");
//                    int d_uid = JSONUtils.getInt(js, MessageKey.d_uid, 0);
//                    int phomId = JSONUtils.getInt(js, MessageKey.phom, 0);
//                    final BINGuiPhomRequest mBINGuiPhomRequest = BINGuiPhomRequest.newBuilder().setMatchID(gmatchID).setDstUid(d_uid).setCards(gcards).setPhomId(phomId).build();
//                    data = mBINGuiPhomRequest.toByteArray();
//                    out.writeShort(MessagesID.GUI_PHOM);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.BOC_PHOM:
//                    int bmatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    final BINBocPhomRequest mBINBocPhomRequest = BINBocPhomRequest.newBuilder().setMatchID(bmatchID).build();
//                    data = mBINBocPhomRequest.toByteArray();
//                    out.writeShort(MessagesID.BOC_PHOM);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.AN_PHOM:
//                    int amatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    final BINAnPhomRequest mBINAnPhomRequest = BINAnPhomRequest.newBuilder().setMatchID(amatchID).build();
//                    data = mBINAnPhomRequest.toByteArray();
//                    out.writeShort(MessagesID.AN_PHOM);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.HA_PHOM:
//                    int hmatchID = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    String hPhom = JSONUtils.getString(js, MessageKey.cards, "");
//                    int hcards = Integer.parseInt(JSONUtils.getString(js, MessageKey.card, "0"));
//                    int uType = JSONUtils.getInt(js, MessageKey.u, 0);
//                    final BINHaPhomRequest mBINHaPhomRequest = BINHaPhomRequest.newBuilder().setMatchID(hmatchID).setUType(uType).setPhoms(hPhom).setCard(hcards).build();
//                    data = mBINHaPhomRequest.toByteArray();
//                    out.writeShort(MessagesID.HA_PHOM);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_SERVER_DATA:
//                    final BINGetServerDataRequest mBINGetServerDataRequest = BINGetServerDataRequest.newBuilder().build();
//                    data = mBINGetServerDataRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_SERVER_DATA);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.CHAT:
//                    int chatmatchID = JSONUtils.getInt(js, MessageKey.room_id, 0);
//                    String ms = JSONUtils.getString(js, MessageKey.message, "");
//                    int type = 0;
//                    final BINChatRequest mBINChatRequest = BINChatRequest.newBuilder().setMatchID(chatmatchID).setType(type).setMessage(ms).build();
//                    data = mBINChatRequest.toByteArray();
//                    out.writeShort(MessagesID.CHAT);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.CHAT_PRIVATE:
//                    int source_uid = JSONUtils.getInt(js, MessageKey.source_uid, 0);
//                    int dest_uid = JSONUtils.getInt(js, MessageKey.dest_uid, 0);
//                    String msPrivate = JSONUtils.getString(js, MessageKey.message, "");
//                    final BINChatPrivateRequest mBINChatPrivateRequest = BINChatPrivateRequest.newBuilder().setDstUid(dest_uid).setSrcUid(source_uid).setMessage(msPrivate).build();
//                    data = mBINChatPrivateRequest.toByteArray();
//                    out.writeShort(MessagesID.CHAT_PRIVATE);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_LOST:
//                    int match_id = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    int player_friend_id = JSONUtils.getInt(js, MessageKey.player_friend_id, 0);
//                    final BINLostRequest mBINLostRequest = BINLostRequest.newBuilder().setMatchId(match_id).setDstUid(player_friend_id).build();
//                    data = mBINLostRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_LOST);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_PEACE:
//                    int drawMatch_id = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    int drawId = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    final BINDrawRequest mBINDrawRequest = BINDrawRequest.newBuilder().setMatchId(drawMatch_id).setUid(drawId).build();
//                    data = mBINDrawRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_PEACE);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_PEACE_ACCEPT:
//                    int adrawMatch_id = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    int adrawId = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    boolean isAccept = JSONUtils.getBoolean(js, MessageKey.isAccept, false);
//                    final BINAcceptDrawRequest mBINAcceptDrawRequest = BINAcceptDrawRequest.newBuilder().setUid(adrawId).setMatchId(adrawMatch_id).setIsAccept(isAccept).build();
//                    data = mBINAcceptDrawRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_PEACE_ACCEPT);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_INVITED:
//                    int ivsource_uid = JSONUtils.getInt(js, MessageKey.source_uid, 0);
//                    int ivdest_uid = JSONUtils.getInt(js, MessageKey.dest_uid, 0);
//                    int ivroom_id = JSONUtils.getInt(js, MessageKey.room_id, 0);
//                    final BINInviteRequest mBINInviteRequest = BINInviteRequest.newBuilder().setDstUid(ivdest_uid).setMatchID(ivroom_id).setSrcUid(ivsource_uid).build();
//                    data = mBINInviteRequest.toByteArray();
//                    out.writeShort(MessagesID.MATCH_INVITED);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.KICK_OUT:
//                    int kickuid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    int kickmatch_id = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    final BINKickOutRequest mBINKickOutRequest = BINKickOutRequest.newBuilder().setMatchID(kickmatch_id).setUid(kickuid).build();
//                    data = mBINKickOutRequest.toByteArray();
//                    out.writeShort(MessagesID.KICK_OUT);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_CHARGING:
//                    out.writeShort(MessagesID.GET_CHARGING);
//                    String ccp = JSONUtils.getString(js, MessageKey.cp, Config.CP);
//                    final BINChargingRequest mBINChargingRequest = BINChargingRequest.newBuilder().setCp(ccp).build();
//                    data = mBINChargingRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_AVATAR_LIST:
//                    int start = JSONUtils.getInt(js, MessageKey.start, 0);
//                    int length = JSONUtils.getInt(js, MessageKey.length, 0);
//                    final BINGetAvatarListRequest mBINGetAvatarListRequest = BINGetAvatarListRequest.newBuilder().setStart(start).setLen(length).build();
//                    data = mBINGetAvatarListRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_AVATAR_LIST);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_USER_DATA:
//                    BINGetUserDataRequest mBINGetUserDataRequest = null;
//                    int action = JSONUtils.getInt(js, MessageKey.action, 0);
//                    if (js.has(MessageKey.cardId)) {
//                        int cardId = JSONUtils.getInt(js, MessageKey.cardId, 0);
//                        String code = JSONUtils.getString(js, MessageKey.code, "");
//                        String serial = JSONUtils.getString(js, MessageKey.serial, "");
//                        mBINGetUserDataRequest = BINGetUserDataRequest.newBuilder().setCardId(cardId).setCode(code).setSerial(serial).setAction(action).build();
//                    } else {
//                        mBINGetUserDataRequest = BINGetUserDataRequest.newBuilder().setAction(action).build();
//                    }
//                    data = mBINGetUserDataRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_USER_DATA);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.BUY_AVATAR:
//                    int avatar = JSONUtils.getInt(js, MessageKey.avatar, 0);
//                    final BINBuyAvatarRequest mBINBuyAvatarRequest = BINBuyAvatarRequest.newBuilder().setAvatarId(avatar).build();
//                    data = mBINBuyAvatarRequest.toByteArray();
//                    out.writeShort(MessagesID.BUY_AVATAR);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.BUY_LEVEL:
//                    final BINBuyLevelRequest mBINBuyLevelRequest = BINBuyLevelRequest.newBuilder().build();
//                    data = mBINBuyLevelRequest.toByteArray();
//                    out.writeShort(MessagesID.BUY_LEVEL);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.POST_LIST:
//                    int blogstart = JSONUtils.getInt(js, MessageKey.start, 0);
//                    int bloglength = JSONUtils.getInt(js, MessageKey.len, 0);
//                    final BINGetPostListRequest mBINGetPostListRequest = BINGetPostListRequest.newBuilder().setStart(blogstart).setLen(bloglength).build();
//                    data = mBINGetPostListRequest.toByteArray();
//                    out.writeShort(MessagesID.POST_LIST);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_USER_INFO:
//                    int infouid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    final BINGetUserInfoRequest mBINGetUserInfoRequest = BINGetUserInfoRequest.newBuilder().setUid(infouid).build();
//                    data = mBINGetUserInfoRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_USER_INFO);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.POST_NEW:
//                    String post = JSONUtils.getString(js, MessageKey.post, "");
//                    final BINPostNewRequest mBINPostNewRequest = BINPostNewRequest.newBuilder().setMessage(post).build();
//                    data = mBINPostNewRequest.toByteArray();
//                    out.writeShort(MessagesID.POST_NEW);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.COMMENT_POST:
//                    String postcommnet = JSONUtils.getString(js, MessageKey.post, "");
//                    int postID = JSONUtils.getInt(js, MessageKey.postID, 0);
//                    final BINPostCommentRequest mBINPostCommentRequest = BINPostCommentRequest.newBuilder().setMessage(postcommnet).setPostId(postID).build();
//                    data = mBINPostCommentRequest.toByteArray();
//                    out.writeShort(MessagesID.COMMENT_POST);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.POST_DETAIL:
//                    int postdetailID = JSONUtils.getInt(js, MessageKey.postID, 0);
//                    final BINPostDetailRequest mBINPostDetailRequest = BINPostDetailRequest.newBuilder().setPostId(postdetailID).build();
//                    data = mBINPostDetailRequest.toByteArray();
//                    out.writeShort(MessagesID.POST_DETAIL);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_RICHESTS:
//                    final BINTopRequest mBINTopRich = BINTopRequest.newBuilder().build();
//                    data = mBINTopRich.toByteArray();
//                    out.writeShort(MessagesID.GET_RICHESTS);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_TOP_SUKIEN:
//                    final BINTopRequest mBINTopSuKien = BINTopRequest.newBuilder().build();
//                    data = mBINTopSuKien.toByteArray();
//                    out.writeShort(MessagesID.GET_TOP_SUKIEN);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_BEST_PLAYER:
//                    final BINTopRequest mBINTopRebest = BINTopRequest.newBuilder().build();
//                    data = mBINTopRebest.toByteArray();
//                    out.writeShort(MessagesID.GET_BEST_PLAYER);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_MOST_PLAYING:
//                    final BINTopRequest mBINTopMost = BINTopRequest.newBuilder().build();
//                    data = mBINTopMost.toByteArray();
//                    out.writeShort(MessagesID.GET_MOST_PLAYING);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.ADD_FRIEND:
//                    int friend_uid = JSONUtils.getInt(js, MessageKey.friend_uid, 0);
//                    final BINAddFriendRequest mBINAddFriendRequest = BINAddFriendRequest.newBuilder().setUid(friend_uid).build();
//                    data = mBINAddFriendRequest.toByteArray();
//                    out.writeShort(MessagesID.ADD_FRIEND);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.ADD_FRIEND_BY_NAME:
//                    String friend_name = JSONUtils.getString(js, MessageKey.friend_name, "");
//                    final BINAddFriendByNameRequest mBINAddFriendByNameRequest = BINAddFriendByNameRequest.newBuilder().setFriendName(friend_name).build();
//                    data = mBINAddFriendByNameRequest.toByteArray();
//                    out.writeShort(MessagesID.ADD_FRIEND_BY_NAME);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_FRIEND_LIST:
//                    int friendstart = JSONUtils.getInt(js, MessageKey.start, 0);
//                    int friendlen = JSONUtils.getInt(js, MessageKey.len, 15);
//                    boolean forInvite = JSONUtils.getBoolean(js, MessageKey.forInvite, false);
//                    final BINGetFriendListRequest mBINGetFriendListRequest = BINGetFriendListRequest.newBuilder().setLen(friendlen).setStart(friendstart).setOnLine(forInvite).build();
//                    data = mBINGetFriendListRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_FRIEND_LIST);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_FREE_FRIEND_LIST:
//                    final BINGetFriendListRequest mBINGetFreeFriendListRequest = BINGetFriendListRequest.newBuilder().build();
//                    data = mBINGetFreeFriendListRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_FREE_FRIEND_LIST);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.REMOVE_FRIEND:
//                    int removefriend_uid = JSONUtils.getInt(js, MessageKey.friend_uid, 0);
//                    final BINRemoveFriendRequest mBINRemoveFriendRequest = BINRemoveFriendRequest.newBuilder().setUid(removefriend_uid).build();
//                    data = mBINRemoveFriendRequest.toByteArray();
//                    out.writeShort(MessagesID.REMOVE_FRIEND);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.OFFLINE_MESSAGE:
//                    int uid2 = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    final BINOfflineMessageRequest mBINOfflineMessageRequest = BINOfflineMessageRequest.newBuilder().setUid(uid2).build();
//                    data = mBINOfflineMessageRequest.toByteArray();
//                    out.writeShort(MessagesID.OFFLINE_MESSAGE);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_ANNOUNCEMENT:
//                    BINGetAnnouncementRequest mBINGetAnnouncementRequest;
//                    if (js.has(MessageKey.announce_id)) {
//                        int announce_id = JSONUtils.getInt(js, MessageKey.announce_id, 0);
//                        mBINGetAnnouncementRequest = BINGetAnnouncementRequest.newBuilder().setReadId(announce_id).build();
//                    } else {
//                        mBINGetAnnouncementRequest = BINGetAnnouncementRequest.newBuilder().build();
//                    }
//                    data = mBINGetAnnouncementRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_ANNOUNCEMENT);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.CLAN_MESSAGE:
//                    out.writeShort(MessagesID.CLAN_MESSAGE);
//                    BINClanRequest mBINClanRequest = null;
//                    int cmd = JSONUtils.getInt(js, MessageKey.cmd, 0);
//                    switch (cmd) {
//                        case ClanConstants.CREATE_CLAN:
//                            String clanname = JSONUtils.getString(js, MessageKey.name, "");
//                            String clantag = JSONUtils.getString(js, MessageKey.tag, "");
//                            String clandesc = JSONUtils.getString(js, MessageKey.desc, "");
//                            int createuid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setUid(createuid).setName(clanname).setDes(clandesc).setTag(clantag).setClanAction(ClanConstants.CREATE_CLAN).build();
//                            break;
//                        case ClanConstants.UPDATE_CLAN_INFO:
//                            String upname = JSONUtils.getString(js, MessageKey.name, "");
//                            String uptag = JSONUtils.getString(js, MessageKey.tag, "");
//                            String updesc = JSONUtils.getString(js, MessageKey.desc, "");
//                            int upclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//
//                            mBINClanRequest = BINClanRequest.newBuilder().setName(upname).setDes(updesc).setTag(uptag).setClanId(upclanId).setClanAction(ClanConstants.UPDATE_CLAN_INFO).build();
//                            break;
//                        case ClanConstants.CLAN_LIST:
//                            int clanstart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int clanlength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setLen(clanlength).setStart(clanstart).setClanAction(ClanConstants.CLAN_LIST).build();
//                            break;
//                        case ClanConstants.INVITATION_LIST:
//                            int inviteuserId = JSONUtils.getInt(js, MessageKey.userId, 0);
//                            int inviteclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int invitestart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int invitelength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(inviteclanId).setUid(inviteuserId).setLen(invitelength).setStart(invitestart).setClanAction(ClanConstants.INVITATION_LIST).build();
//                            break;
//                        case ClanConstants.APPLICATION_LIST:
//                            int apluserId = JSONUtils.getInt(js, MessageKey.userId, 0);
//                            int aplclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int aplstart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int apllength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(aplclanId).setUid(apluserId).setLen(apllength).setStart(aplstart).setClanAction(ClanConstants.APPLICATION_LIST).build();
//                            break;
//                        case ClanConstants.USER_INVITATION_LIST:
//                            int ivluserId = JSONUtils.getInt(js, MessageKey.userId, 0);
//                            int ivlclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int ivlstart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int ivllength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(ivlclanId).setUid(ivluserId).setLen(ivllength).setStart(ivlstart).setClanAction(ClanConstants.USER_INVITATION_LIST).build();
//                            break;
//                        case ClanConstants.CLAN_MEMBER_LIST:
//                            int memberclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int memberstart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int memberlength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(memberclanId).setLen(memberlength).setStart(memberstart).setClanAction(ClanConstants.CLAN_MEMBER_LIST).build();
//                            break;
//                        case ClanConstants.GET_MY_CLAN:
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanAction(ClanConstants.GET_MY_CLAN).build();
//                            break;
//                        case ClanConstants.GET_CLAN_INFO:
//                            int inforclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(inforclanId).setClanAction(ClanConstants.GET_CLAN_INFO).build();
//                            break;
//                        case ClanConstants.APPLY_TO_A_CLAN:
//                            int applyclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int applyuid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(applyclanId).setUid(applyuid).setClanAction(ClanConstants.APPLY_TO_A_CLAN).build();
//                            break;
//                        case ClanConstants.CLAN_APPLICATION_LIST:
//                            int apuserId = JSONUtils.getInt(js, MessageKey.userId, 0);
//                            int apclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int apstart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int aplength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setLen(aplength).setStart(apstart).setClanId(apclanId).setUid(apuserId).setClanAction(ClanConstants.CLAN_APPLICATION_LIST).build();
//                            break;
//                        case ClanConstants.RESPONSE_TO_A_MESSAGE:
//                            int resclanId = JSONUtils.getInt(js, MessageKey.clanId, -1);
//                            int resuid = JSONUtils.getInt(js, MessageKey.uid, -1);
//                            int resaction = JSONUtils.getInt(js, MessageKey.action, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setSubAction(resaction).setClanId(resclanId).setUid(resuid).setClanAction(ClanConstants.RESPONSE_TO_A_MESSAGE).build();
//                            break;
//                        case ClanConstants.INVITE:
//                            int invitetoclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int inviteId = JSONUtils.getInt(js, MessageKey.inviteId, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(invitetoclanId).setUid(inviteId).setClanAction(ClanConstants.INVITE).build();
//                            break;
//                        case ClanConstants.KICK:
//                            int kickclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int kickId = JSONUtils.getInt(js, MessageKey.kickId, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(kickclanId).setUid(kickId).setClanAction(ClanConstants.KICK).build();
//                            break;
//                        case ClanConstants.QUIT:
//                            int quitclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int quitkId = JSONUtils.getInt(js, MessageKey.uid, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(quitclanId).setUid(quitkId).setClanAction(ClanConstants.QUIT).build();
//                            break;
//                        case ClanConstants.DONATE:
//                            int donateclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int donateId = JSONUtils.getInt(js, MessageKey.uid, 0);
//                            long donationAmount = js.getLong(MessageKey.donationAmount);
//                            mBINClanRequest = BINClanRequest.newBuilder().setDonationAmount(donationAmount).setClanId(donateclanId).setUid(donateId).setClanAction(ClanConstants.DONATE).build();
//                            break;
//                        case ClanConstants.UPGRADE_CLAN_LEVEL:
//                            int levelclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setClanId(levelclanId).setClanAction(ClanConstants.UPGRADE_CLAN_LEVEL).build();
//                            break;
//                        case ClanConstants.INVITEABLE_LIST:
//                            int invitableid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                            int invitableclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int invitablestart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int invitablelength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setLen(invitablelength).setStart(invitablestart).setUid(invitableid).setClanId(invitableclanId).setClanAction(ClanConstants.INVITEABLE_LIST).build();
//                            break;
//                        case ClanConstants.POST_LIST:
//                            int postclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int poststart = JSONUtils.getInt(js, MessageKey.start, 0);
//                            int postlength = JSONUtils.getInt(js, MessageKey.length, 0);
//                            mBINClanRequest = BINClanRequest.newBuilder().setLen(postlength).setStart(poststart).setClanId(postclanId).setClanAction(ClanConstants.POST_LIST).build();
//                            break;
//                        case ClanConstants.POST_NEW:
//                            int newclanId = JSONUtils.getInt(js, MessageKey.clanId, 0);
//                            int userId = JSONUtils.getInt(js, MessageKey.userId, 0);
//                            String msg = JSONUtils.getString(js, MessageKey.message, "");
//                            mBINClanRequest = BINClanRequest.newBuilder().setMessage(msg).setUid(userId).setClanId(newclanId).setClanAction(ClanConstants.POST_NEW).build();
//                            break;
//                    }
//                    data = mBINClanRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MATCH_REPLY:
//                    int rpmatch_id = JSONUtils.getInt(js, MessageKey.match_id, 0);
//                    boolean is_accept = JSONUtils.getBoolean(js, MessageKey.is_accept, false);
//                    int buddy_uid = JSONUtils.getInt(js, MessageKey.buddy_uid, 0);
//                    int rpuid = JSONUtils.getInt(js, MessageKey.uid, 0);
//                    out.writeShort(MessagesID.MATCH_REPLY);
//                    final BINReplyRequest mBINReplyRequest = BINReplyRequest.newBuilder().setUid(rpuid).setSrcUid(buddy_uid).setMatchID(rpmatch_id).setIsAccept(is_accept).build();
//                    data = mBINReplyRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.TRANSFER_CASH:
//                    int cashdesc_uid = JSONUtils.getInt(js, MessageKey.desc_uid, 0);
//                    long money = js.getLong(MessageKey.money);
//                    final BINTransferCashRequest mBINTransferCashRequest = BINTransferCashRequest.newBuilder().setDstUid(cashdesc_uid).setMoney(money).build();
//                    data = mBINTransferCashRequest.toByteArray();
//                    out.writeShort(MessagesID.TRANSFER_CASH);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.UPDATE_USER_INFO:
//                    String new_password = JSONUtils.getString(js, MessageKey.new_password, "");
//                    String old_password = JSONUtils.getString(js, MessageKey.old_password, "");
//                    int sex = JSONUtils.getInt(js, MessageKey.sex, 1);
//                    String phone = JSONUtils.getString(js, MessageKey.PhoneNumber, "");
//                    String fullname = JSONUtils.getString(js, MessageKey.fullname, "");
//                    String email = JSONUtils.getString(js, MessageKey.email, "");
//                    String cmnd = JSONUtils.getString(js, MessageKey.cmnd, "");
//
//                    final BINUpdateInfoRequest mBINUpdateInfoRequest = BINUpdateInfoRequest.newBuilder()
//                            .setNewPass(new_password)
//                            .setOldPass(old_password)
//                            .setSex(sex)
//                            .setPhone(phone)
//                            .setFullName(fullname)
//                            .setEmail(email)
//                            .setCmnd(cmnd)
//                            .build();
//                    data = mBINUpdateInfoRequest.toByteArray();
//
//                    out.writeShort(MessagesID.UPDATE_USER_INFO);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_ROOM_MONEY:
//                    final BINGetHelpRequest mBINGetHelpRequest = BINGetHelpRequest.newBuilder().setGetHelp(true).build();
//                    data = mBINGetHelpRequest.toByteArray();
//                    out.writeShort(MessagesID.GET_ROOM_MONEY);
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.PING:
//                    out.writeShort(MessagesID.PING);
//                    final BINPingRequest mBINPingRequest;
//                    if (js.has(MessageKey.timeout)) {
//                        mBINPingRequest = BINPingRequest.newBuilder().setDisTime(JSONUtils.getInt(js, MessageKey.timeout, 0)).build();
//                    } else {
//                        mBINPingRequest = BINPingRequest.newBuilder().setAction(0).build();
//                    }
//                    data = mBINPingRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_EXCHANGE_GIFT:
//                    out.writeShort(MessagesID.GET_EXCHANGE_GIFT);
//                    final BINDoiThuongRequest exchangeRequest = BINDoiThuongRequest.newBuilder()
//                            .setCp(js.getString(MessageKey.cp)).build();
//                    data = exchangeRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.DO_EXCHANGE_GIFT:
//                    out.writeShort(MessagesID.DO_EXCHANGE_GIFT);
//                    final BINActionDoiThuongRequest doExchangeRequest = BINActionDoiThuongRequest.newBuilder()
//                            .setUId(js.getInt(MessageKey.uid))
//                            .setCpId(js.getInt(MessageKey.nhaMangId))
//                            .setId(js.getInt(MessageKey.tyGiaId))
//                            .build();
//                    data = doExchangeRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_GAME_CONFIG:
//                    out.writeShort(MessagesID.GET_GAME_CONFIG);
//                    final BINGameConfigRequest gameConfigRequest = BINGameConfigRequest.newBuilder()
//                            .setCp(js.getString(MessageKey.cp))
//                            .setClientType(Config.clientType)
//                            .build();
//                    data = gameConfigRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_GAME_HISTORY:
//                    out.writeShort(MessagesID.GET_GAME_HISTORY);
//                    final BINGetGameHistoryRequest gameHistoryRequest = BINGetGameHistoryRequest.newBuilder()
//                            .setCurrentPage(js.getInt(MessageKey.currentPage))
//                            .setType(js.getInt(MessageKey.type))
//                            .build();
//                    data = gameHistoryRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.LOAD_APP_STORE_ITEM:
//                    out.writeShort(MessagesID.LOAD_APP_STORE_ITEM);
//                    final BINLoadAppStoreItemRequest loadAppStoreRequest = BINLoadAppStoreItemRequest.newBuilder()
//                            .setClientType(js.getInt(MessageKey.clientType))
//                            .build();
//                    data = loadAppStoreRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.CHARGE_APP_STORE:
//                    out.writeShort(MessagesID.CHARGE_APP_STORE);
//                    final BINChargeAppStoreRequest chargeAppStoreRequest = BINChargeAppStoreRequest.newBuilder()
//                            .setOrderId(js.getString(MessageKey.orderId))
//                            .setPackageName(js.getString(MessageKey.packageName))
//                            .setProductId(js.getString(MessageKey.productId))
//                            .setPurchaseTime((float) js.getDouble(MessageKey.purchaseTime))
//                            .setPurchaseState(js.getInt(MessageKey.purchaseState))
//                            .setPayload(js.getString(MessageKey.purchasePayload))
//                            .setTokenPurchase(js.getString(MessageKey.purchaseToken))
//                            .build();
//                    data = chargeAppStoreRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.CHARGE_GIFT_CODE:
//                    out.writeShort(MessagesID.CHARGE_GIFT_CODE);
//                    final BINChargeGiftCodeRequest chargeGiftCodeRequest = BINChargeGiftCodeRequest.newBuilder()
//                            .setGiftcode(js.getString(MessageKey.giftcode))
//                            .build();
//                    data = chargeGiftCodeRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.TAIXIU_BET:
//                    out.writeShort(MessagesID.TAIXIU_BET);
//                    final BINTaiXiuBetRequest betRequest = BINTaiXiuBetRequest.newBuilder()
//                            .setCash(js.getLong(MessageKey.cash))
//                            .setTaixiu(js.getBoolean(MessageKey.taixiu))
//                            .setUid(js.getLong(MessageKey.uid))
//                            .setZoneId(js.getInt(MessageKey.zoneId))
//                            .build();
//                    data = betRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.TAIXIU_GET:
//                    out.writeShort(MessagesID.TAIXIU_GET);
//                    final BINTaiXiuGetRequest taiXiuGetRequest = BINTaiXiuGetRequest.newBuilder()
//                            .setUid(js.getLong(MessageKey.uid))
//                            .setZoneId(js.getInt(MessageKey.zoneId))
//                            .build();
//                    data = taiXiuGetRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MINI_GAME_HISTORY:
//                    out.writeShort(MessagesID.MINI_GAME_HISTORY);
//                    final BINMiniGameHistoryRequest miniGameHistoryRequest = BINMiniGameHistoryRequest.newBuilder()
//                            .setGame_id(js.getInt(MessageKey.game_id))
//                            .setCurrentPage(js.getInt(MessageKey.currentPage))
//                            .build();
//                    data = miniGameHistoryRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.MINI_GAME_TOPRANK:
//                    out.writeShort(MessagesID.MINI_GAME_TOPRANK);
//                    final BINMiniGameTopRankRequest miniGameTopRankRequest = BINMiniGameTopRankRequest.newBuilder()
//                            .setGame_id(js.getInt(MessageKey.game_id))
//                            .build();
//                    data = miniGameTopRankRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_QUY_THUONG_MINIGAME:
//                    out.writeShort(MessagesID.GET_QUY_THUONG_MINIGAME);
//                    final BINGetQuyThuongMiniGameRequest getQuyThuongMiniGameRequest = BINGetQuyThuongMiniGameRequest.newBuilder()
//                            .setGameId(js.getInt(MessageKey.gameId))
//                            .setBetCash(js.getInt(MessageKey.betCash))
//                            .build();
//                    data = getQuyThuongMiniGameRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.BET_MINI_POKER:
//                    out.writeShort(MessagesID.BET_MINI_POKER);
//                    final BINBetMiniPokerRequest betMiniPokerRequest = BINBetMiniPokerRequest.newBuilder()
//                            .setGameId(js.getInt(MessageKey.gameId))
//                            .setBetCash(js.getInt(MessageKey.betCash))
//                            .build();
//                    data = betMiniPokerRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_MINI_POKER_TOP_HISTORY:
//                    out.writeShort(MessagesID.GET_MINI_POKER_TOP_HISTORY);
//                    final BINGetMiniPokerTopHistoryRequest getMiniPokerTopHistoryRequest = BINGetMiniPokerTopHistoryRequest.newBuilder()
//                            .setCurrentPage(js.getInt(MessageKey.currentPage))
//                            .setTypeTop(js.getInt(MessageKey.typeTop))
//                            .setZoneId(js.getInt(MessageKey.gameId))
//                            .build();
//                    data = getMiniPokerTopHistoryRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.BET_SLOTMACHINE:
//                    out.writeShort(MessagesID.BET_SLOTMACHINE);
//                    final BINBetSlotMachineRequest betSlotMachineRequest = BINBetSlotMachineRequest.newBuilder()
//                            .setBetCash(js.getInt(MessageKey.betCash))
//                            .setBetLine(js.getString(MessageKey.betLine))
//                            .setGameId(js.getInt(MessageKey.gameId))
//                            .build();
//                    data = betSlotMachineRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_SLOTMACHINE_TOP:
//                    out.writeShort(MessagesID.GET_SLOTMACHINE_TOP);
//                    final BINGetSlotMachineTopRequest getSlotMachineTopRequest = BINGetSlotMachineTopRequest.newBuilder()
//                            .setCurrentPage(js.getInt(MessageKey.currentPage))
//                            .setGameId(js.getInt(MessageKey.gameId))
//                            .build();
//                    data = getSlotMachineTopRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.GET_SLOTMACHINE_HISTORY:
//                    out.writeShort(MessagesID.GET_SLOTMACHINE_HISTORY);
//                    final BINGetSlotMachineHistoryRequest getSlotMachineHistoryRequest = BINGetSlotMachineHistoryRequest.newBuilder()
//                            .setCurrentPage(js.getInt(MessageKey.currentPage))
//                            .setGameId(js.getInt(MessageKey.gameId))
//                            .build();
//                    data = getSlotMachineHistoryRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//                case MessagesID.BUY_XU:
//                    out.writeShort(MessagesID.BUY_XU);
//                    final BINBuyXuRequest binBuyXuRequest = BINBuyXuRequest.newBuilder()
//                            .setCashSu(js.getLong(MessageKey.cashSu))
//                            .build();
//                    data = binBuyXuRequest.toByteArray();
//                    out.write(data);
//                    return bin.toByteArray();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bin != null) {
                bin.close();
                bin = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
        }
        return null;
    }
}
