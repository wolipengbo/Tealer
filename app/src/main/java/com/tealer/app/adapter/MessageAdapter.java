package com.tealer.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.chat.EMNormalFileMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.DateUtils;
import com.hyphenate.util.FileUtils;
import com.hyphenate.util.LatLng;
import com.hyphenate.util.TextFormater;
import com.tealer.app.Constant;
import com.tealer.app.R;
import com.tealer.app.activity.BaiduMapActivity;
import com.tealer.app.activity.FXAlertDialog;
import com.tealer.app.activity.ShowBigImage;
import com.tealer.app.activity.ShowNormalFileActivity;
import com.tealer.app.activity.ShowVideoActivity;
import com.tealer.app.fx.ChatActivity;
import com.tealer.app.fx.others.BaseImageListAdapter;
import com.tealer.app.task.LoadImageTask;
import com.tealer.app.task.LoadVideoImageTask;
import com.tealer.app.utils.BitmapDisplayConfigHelper;
import com.tealer.app.utils.ImageCache;
import com.tealer.app.utils.ImageLoaderHelper;
import com.tealer.app.utils.ImageUtils;
import com.tealer.app.utils.PreferenceManager;
import com.tealer.app.utils.SmileUtils;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lipengbo
 * @date 2016/3/22
 * @intro 简介
 */
public class MessageAdapter extends BaseImageListAdapter {

    private final static String TAG = "msg";

    private static final int HANDLER_MESSAGE_REFRESH_LIST = 0;
    private static final int HANDLER_MESSAGE_SELECT_LAST = 1;
    private static final int HANDLER_MESSAGE_SEEK_TO = 2;

    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_SENT_LOCATION = 3;
    private static final int MESSAGE_TYPE_RECV_LOCATION = 4;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 5;
    private static final int MESSAGE_TYPE_SENT_VOICE = 6;
    private static final int MESSAGE_TYPE_RECV_VOICE = 7;
    private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
    private static final int MESSAGE_TYPE_SENT_FILE = 10;
    private static final int MESSAGE_TYPE_RECV_FILE = 11;
    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 12;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 13;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 14;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 15;

    public static final String IMAGE_DIR = "chat/image/";
    public static final String VOICE_DIR = "chat/audio/";
    public static final String VIDEO_DIR = "chat/video";
    EMMessage[] messages = null;
    private String username;
    private LayoutInflater inflater;
    private Activity activity;

    // reference to conversation object in chatsdk
    private EMConversation conversation;

    private Context context;
    private ListView listView;

    private Map<String, Timer> timers = new Hashtable<String, Timer>();

    public MessageAdapter(Context context, String username, int chatType,ListView listView) {
        this.username = username;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listView=listView;
        activity = (Activity) context;
        this.conversation = EMClient.getInstance().chatManager().getConversation(
                username);
    }


    Handler handler = new Handler() {
        private void refreshList() {
            // UI线程不能直接使用conversation.getAllMessages()
            // 否则在UI刷新过程中，如果收到新的消息，会导致并发问题
            messages = (EMMessage[]) conversation.getAllMessages().toArray(new EMMessage[0]);
            conversation.markAllMessagesAsRead();
            notifyDataSetChanged();
        }

        @Override
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case HANDLER_MESSAGE_REFRESH_LIST:
                    refreshList();
                    break;
                case HANDLER_MESSAGE_SELECT_LAST:
                    if (messages.length > 0) {
                        listView.setSelection(messages.length - 1);
                    }
                    break;
                case HANDLER_MESSAGE_SEEK_TO:
                    int position = message.arg1;
                    listView.setSelection(position);
                    break;
                default:
                    break;
            }
        }
    };




    /**
     * 刷新页面
     */
    public void refresh() {
        if (handler.hasMessages(HANDLER_MESSAGE_REFRESH_LIST)) {
            return;
        }
        android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST);
        handler.sendMessage(msg);
    }

    /**
     * 刷新页面, 选择最后一个
     */
    public void refreshSelectLast() {
        // avoid refresh too frequently when receiving large amount offline messages
        final int TIME_DELAY_REFRESH_SELECT_LAST = 100;
        handler.removeMessages(HANDLER_MESSAGE_REFRESH_LIST);
        handler.removeMessages(HANDLER_MESSAGE_SELECT_LAST);
        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_REFRESH_LIST, TIME_DELAY_REFRESH_SELECT_LAST);
        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_SELECT_LAST, TIME_DELAY_REFRESH_SELECT_LAST);
    }

    /**
     * 刷新页面, 选择Position
     */
    public void refreshSeekTo(int position) {
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST));
        android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_SEEK_TO);
        msg.arg1 = position;
        handler.sendMessage(msg);
    }




    // public void setUser(String user) {
    // this.user = user;
    // }

    /**
     * 获取item数
     */
    public int getCount() {
        return messages == null ? 0 : messages.length;
    }



    public EMMessage getItem(int position) {
        if (messages != null && position < messages.length) {
            return messages[position];
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取item类型
     */
    public int getItemViewType(int position) {
        EMMessage message = getItem(position);
        if (message == null) {
            return -1;
        }
        if (message.getType() == EMMessage.Type.TXT) {
            if (message.getBooleanAttribute(
                    Constant.MESSAGE_ATTR_IS_VOICE_CALL, false))
                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL
                        : MESSAGE_TYPE_SENT_VOICE_CALL;
            else if (message.getBooleanAttribute(
                    Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false))
                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL
                        : MESSAGE_TYPE_SENT_VIDEO_CALL;
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT
                    : MESSAGE_TYPE_SENT_TXT;
        }
        if (message.getType() == EMMessage.Type.IMAGE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE
                    : MESSAGE_TYPE_SENT_IMAGE;

        }
        if (message.getType() == EMMessage.Type.LOCATION) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_LOCATION
                    : MESSAGE_TYPE_SENT_LOCATION;
        }
        if (message.getType() == EMMessage.Type.VOICE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE
                    : MESSAGE_TYPE_SENT_VOICE;
        }
        if (message.getType() == EMMessage.Type.VIDEO) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO
                    : MESSAGE_TYPE_SENT_VIDEO;
        }
        if (message.getType() == EMMessage.Type.FILE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FILE
                    : MESSAGE_TYPE_SENT_FILE;
        }

        return -1;// invalid
    }

    public int getViewTypeCount() {
        return 16;
    }

    @SuppressLint("InflateParams")
    private View createViewByMessage(EMMessage message, int position) {
        switch (message.getType()) {
            case LOCATION:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_location, null) : inflater
                        .inflate(R.layout.row_sent_location, null);
            case IMAGE:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_picture, null) : inflater
                        .inflate(R.layout.row_sent_picture, null);

            case VOICE:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_voice, null) : inflater
                        .inflate(R.layout.row_sent_voice, null);
            case VIDEO:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_video, null) : inflater
                        .inflate(R.layout.row_sent_video, null);
            case FILE:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_file, null) : inflater
                        .inflate(R.layout.row_sent_file, null);
            default:
                // 语音电话
                if (message.getBooleanAttribute(
                        Constant.MESSAGE_ATTR_IS_VOICE_CALL, false))
                    return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                            .inflate(R.layout.row_received_voice_call, null)
                            : inflater.inflate(R.layout.row_sent_voice_call, null);
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_message, null) : inflater
                        .inflate(R.layout.row_sent_message, null);
        }
    }

    @SuppressLint("NewApi")
    public View getView(final int position, View convertView, ViewGroup parent) {
        final EMMessage message = getItem(position);
        EMMessage.ChatType chatType = message.getChatType();

        String fromusernick = "0000";
        String fromuseravatar = "0000";

        EMMessage.Direct msg_dirct = message.direct();

        try {
            fromusernick = message.getStringAttribute("usernick");
            fromuseravatar = message.getStringAttribute("useravatar");

        } catch (HyphenateException e) {
            e.printStackTrace();
        }

        // Log.e("message.getFrom()---->>>>.",message.getFrom());
        if (message.getFrom().equals("admin")) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.social_chat_admin_item, null);
            TextView timestamp = (TextView) convertView
                    .findViewById(R.id.tv_time);
            TextView tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            timestamp.setText(DateUtils.getTimestampString(new Date(message
                    .getMsgTime())));
            EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
            Spannable span = SmileUtils.getSmiledText(context,
                    txtBody.getMessage());
            // 设置内容
            tv_content.setText(span, TextView.BufferType.SPANNABLE);
            return convertView;

        } else {

            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = createViewByMessage(message, position);
                if (message.getType() == EMMessage.Type.IMAGE) {
                    try {
                        holder.iv = ((ImageView) convertView
                                .findViewById(R.id.iv_sendPicture));
                        holder.head_iv = (ImageView) convertView
                                .findViewById(R.id.iv_userhead);
                        holder.tv = (TextView) convertView
                                .findViewById(R.id.percentage);
                        holder.pb = (ProgressBar) convertView
                                .findViewById(R.id.progressBar);
                        holder.staus_iv = (ImageView) convertView
                                .findViewById(R.id.msg_status);
                        holder.tv_userId = (TextView) convertView
                                .findViewById(R.id.tv_userid);
                    } catch (Exception e) {
                    }

                } else if (message.getType() == EMMessage.Type.TXT) {

                    try {
                        holder.pb = (ProgressBar) convertView
                                .findViewById(R.id.pb_sending);
                        holder.staus_iv = (ImageView) convertView
                                .findViewById(R.id.msg_status);
                        holder.head_iv = (ImageView) convertView
                                .findViewById(R.id.iv_userhead);
                        // 这里是文字内容
                        holder.tv = (TextView) convertView
                                .findViewById(R.id.tv_chatcontent);
                        holder.tv_userId = (TextView) convertView
                                .findViewById(R.id.tv_userid);
                    } catch (Exception e) {
                    }

                    // 语音通话及视频通话
                    if (message.getBooleanAttribute(
                            Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)
                            || message.getBooleanAttribute(
                            Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                        holder.iv = (ImageView) convertView
                                .findViewById(R.id.iv_call_icon);
                        holder.tv = (TextView) convertView
                                .findViewById(R.id.tv_chatcontent);
                    }

                } else if (message.getType() == EMMessage.Type.VOICE) {
                    try {
                        holder.iv = ((ImageView) convertView
                                .findViewById(R.id.iv_voice));
                        holder.head_iv = (ImageView) convertView
                                .findViewById(R.id.iv_userhead);
                        holder.tv = (TextView) convertView
                                .findViewById(R.id.tv_length);
                        holder.pb = (ProgressBar) convertView
                                .findViewById(R.id.pb_sending);
                        holder.staus_iv = (ImageView) convertView
                                .findViewById(R.id.msg_status);
                        holder.tv_userId = (TextView) convertView
                                .findViewById(R.id.tv_userid);
                        holder.iv_read_status = (ImageView) convertView
                                .findViewById(R.id.iv_unread_voice);
                    } catch (Exception e) {
                    }
                } else if (message.getType() == EMMessage.Type.LOCATION) {
                    try {
                        holder.head_iv = (ImageView) convertView
                                .findViewById(R.id.iv_userhead);
                        holder.tv = (TextView) convertView
                                .findViewById(R.id.tv_location);
                        holder.pb = (ProgressBar) convertView
                                .findViewById(R.id.pb_sending);
                        holder.staus_iv = (ImageView) convertView
                                .findViewById(R.id.msg_status);
                        holder.tv_userId = (TextView) convertView
                                .findViewById(R.id.tv_userid);
                    } catch (Exception e) {
                    }
                } else if (message.getType() == EMMessage.Type.VIDEO) {
                    try {
                        holder.iv = ((ImageView) convertView
                                .findViewById(R.id.chatting_content_iv));
                        holder.head_iv = (ImageView) convertView
                                .findViewById(R.id.iv_userhead);
                        holder.tv = (TextView) convertView
                                .findViewById(R.id.percentage);
                        holder.pb = (ProgressBar) convertView
                                .findViewById(R.id.progressBar);
                        holder.staus_iv = (ImageView) convertView
                                .findViewById(R.id.msg_status);
                        holder.size = (TextView) convertView
                                .findViewById(R.id.chatting_size_iv);
                        holder.timeLength = (TextView) convertView
                                .findViewById(R.id.chatting_length_iv);
                        holder.playBtn = (ImageView) convertView
                                .findViewById(R.id.chatting_status_btn);
                        holder.container_status_btn = (LinearLayout) convertView
                                .findViewById(R.id.container_status_btn);
                        holder.tv_userId = (TextView) convertView
                                .findViewById(R.id.tv_userid);

                    } catch (Exception e) {
                    }
                } else if (message.getType() == EMMessage.Type.FILE) {
                    try {
                        holder.head_iv = (ImageView) convertView
                                .findViewById(R.id.iv_userhead);
                        holder.tv_file_name = (TextView) convertView
                                .findViewById(R.id.tv_file_name);
                        holder.tv_file_size = (TextView) convertView
                                .findViewById(R.id.tv_file_size);
                        holder.pb = (ProgressBar) convertView
                                .findViewById(R.id.pb_sending);
                        holder.staus_iv = (ImageView) convertView
                                .findViewById(R.id.msg_status);
                        holder.tv_file_download_state = (TextView) convertView
                                .findViewById(R.id.tv_file_state);
                        holder.ll_container = (LinearLayout) convertView
                                .findViewById(R.id.ll_file_container);
                        // 这里是进度值
                        holder.tv = (TextView) convertView
                                .findViewById(R.id.percentage);
                    } catch (Exception e) {
                    }
                    try {
                        holder.tv_userId = (TextView) convertView
                                .findViewById(R.id.tv_userid);
                    } catch (Exception e) {
                    }

                }

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 群聊时，显示接收的消息的发送人的名称
            if (chatType == EMMessage.ChatType.GroupChat
                    && message.direct() == EMMessage.Direct.RECEIVE)
                // demo用username代替nick
                holder.tv_userId.setText(fromusernick);

            // 如果是发送的消息并且不是群聊消息，显示已读textview
            if (message.direct() == EMMessage.Direct.SEND
                    && chatType != EMMessage.ChatType.GroupChat) {
                holder.tv_ack = (TextView) convertView
                        .findViewById(R.id.tv_ack);
                holder.tv_delivered = (TextView) convertView
                        .findViewById(R.id.tv_delivered);
                if (holder.tv_ack != null) {
                    if (message.isAcked()) {
                        if (holder.tv_delivered != null) {
                            holder.tv_delivered.setVisibility(View.INVISIBLE);
                        }
                        holder.tv_ack.setVisibility(View.VISIBLE);
                    } else {
                        holder.tv_ack.setVisibility(View.INVISIBLE);

                        // check and display msg delivered ack status
                        if (holder.tv_delivered != null) {
                            if (message.isDelivered()) {
                                holder.tv_delivered.setVisibility(View.VISIBLE);
                            } else {
                                holder.tv_delivered
                                        .setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            } else {
                // 如果是文本或者地图消息并且不是group messgae，显示的时候给对方发送已读回执
                if ((message.getType() == EMMessage.Type.TXT || message.getType() == Type.LOCATION)
                        && !message.isAcked() && chatType != EMMessage.ChatType.GroupChat) {
                    // 不是语音通话记录
                    if (!message.getBooleanAttribute(
                            Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                        try {
                        EMClient.getInstance().chatManager().ackMessageRead(
                                    message.getFrom(), message.getMsgId());
                            // 发送已读回执
                            message.setAcked(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            switch (message.getType()) {
                // 根据消息type显示item
                case IMAGE: // 图片
                    handleImageMessage(message, holder, position, convertView);
                    break;
                case TXT: // 文本
                    if (message.getBooleanAttribute(
                            Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)
                            || message.getBooleanAttribute(
                            Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false))
                        // 音视频通话
                        handleCallMessage(message, holder, position);
                    else
                        handleTextMessage(message, holder, position);
                    break;
                case LOCATION: // 位置
                    handleLocationMessage(message, holder, position, convertView);
                    break;
                case VOICE: // 语音
                    handleVoiceMessage(message, holder, position, convertView);
                    break;
                case VIDEO: // 视频
                    handleVideoMessage(message, holder, position, convertView);
                    break;
                case FILE: // 一般文件
                    handleFileMessage(message, holder, position, convertView);
                    break;
                default:
                    // not supported
            }

            if (message.direct() == EMMessage.Direct.SEND) {
                View statusView = convertView.findViewById(R.id.msg_status);
                // 重发按钮点击事件
                statusView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 显示重发消息的自定义alertdialog
                        Intent intent = new Intent(activity,
                                FXAlertDialog.class);
                        intent.putExtra("msg",
                                activity.getString(R.string.confirm_resend));
                        intent.putExtra("title",
                                activity.getString(R.string.resend));
                        intent.putExtra("cancel", true);
                        intent.putExtra("position", position);
                        if (message.getType() == EMMessage.Type.TXT)
                            activity.startActivityForResult(intent,
                                    ChatActivity.REQUEST_CODE_TEXT);
                        else if (message.getType() == EMMessage.Type.VOICE)
                            activity.startActivityForResult(intent,
                                    ChatActivity.REQUEST_CODE_VOICE);
                        else if (message.getType() == EMMessage.Type.IMAGE)
                            activity.startActivityForResult(intent,
                                    ChatActivity.REQUEST_CODE_PICTURE);
                        else if (message.getType() == EMMessage.Type.LOCATION)
                            activity.startActivityForResult(intent,
                                    ChatActivity.REQUEST_CODE_LOCATION);
                        else if (message.getType() == EMMessage.Type.FILE)
                            activity.startActivityForResult(intent,
                                    ChatActivity.REQUEST_CODE_FILE);
                        else if (message.getType() == EMMessage.Type.VIDEO)
                            activity.startActivityForResult(intent,
                                    ChatActivity.REQUEST_CODE_VIDEO);

                    }
                });

            } else {
                // 长按头像，移入黑名单
                holder.head_iv
                        .setOnLongClickListener(new View.OnLongClickListener() {

                            @Override
                            public boolean onLongClick(View v) {
                                Intent intent = new Intent(activity,
                                        FXAlertDialog.class);
                                intent.putExtra("msg", "移入到黑名单？");
                                intent.putExtra("cancel", true);
                                intent.putExtra("position", position);
                                activity.startActivityForResult(
                                        intent,
                                        ChatActivity.REQUEST_CODE_ADD_TO_BLACKLIST);
                                return true;
                            }
                        });
            }

            TextView timestamp = (TextView) convertView
                    .findViewById(R.id.timestamp);

            if (position == 0) {
                timestamp.setText(DateUtils.getTimestampString(new Date(message
                        .getMsgTime())));
                timestamp.setVisibility(View.VISIBLE);
            } else {
                // 两条消息时间离得如果稍长，显示时间
                if (DateUtils.isCloseEnough(message.getMsgTime(), messages[position - 1].getMsgTime())) {
                    timestamp.setVisibility(View.GONE);
                } else {
                    timestamp.setText(DateUtils.getTimestampString(new Date(
                            message.getMsgTime())));
                    timestamp.setVisibility(View.VISIBLE);
                }
            }

            // 设置头像。。。
            // holder.head_iv.setImageResource(R.drawable.default_useravatar);

            if (msg_dirct == EMMessage.Direct.RECEIVE) {

                // 对方的头像值： fromuseravatar

                final String avater = Constant.URL_Avatar + fromuseravatar;

                holder.head_iv.setTag(avater);

                if (avater != null && !avater.equals("")) {
                    ImageLoaderHelper.GetInstance().display(holder.head_iv,avater, BitmapDisplayConfigHelper.GetInstance().getIconBitmapUtilsConfig());
                }

            } else {

                // 设置自己本地的头像

                final String avater = Constant.URL_Avatar
                        + PreferenceManager.getInstance().getCurrentUserAvatar();

                holder.head_iv.setTag(avater);

                if (avater != null && !avater.equals("")) {
                    ImageLoaderHelper.GetInstance().display(holder.head_iv,avater, BitmapDisplayConfigHelper.GetInstance().getIconBitmapUtilsConfig());
                }
            }
        }
        return convertView;
    }

    /**
     * 文本消息
     *
     * @param message
     * @param holder
     * @param position
     */
    private void handleTextMessage(EMMessage message, ViewHolder holder,
                                   final int position) {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = SmileUtils
                .getSmiledText(context, txtBody.getMessage());
        // 设置内容
        holder.tv.setText(span, TextView.BufferType.SPANNABLE);
        // 设置长按事件监听
        // holder.tv.setOnLongClickListener(new OnLongClickListener() {
        // @Override
        // public boolean onLongClick(View v) {
        // activity.startActivityForResult((new Intent(activity,
        // ContextMenu.class)).putExtra("position", position)
        // .putExtra("type", EMMessage.Type.TXT.ordinal()),
        // ChatActivity.REQUEST_CODE_CONTEXT_MENU);
        // return true;
        // }
        // });

        if (message.direct() == EMMessage.Direct.SEND) {
            switch (message.status()) {
                case SUCCESS: // 发送成功
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.GONE);
                    break;
                case FAIL: // 发送失败
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS: // 发送中
                    holder.pb.setVisibility(View.VISIBLE);
                    holder.staus_iv.setVisibility(View.GONE);
                    break;
                default:
                    // 发送消息
                    sendMsgInBackground(message, holder);
            }
        }
    }

    /**
     * 音视频通话记录
     *
     * @param message
     * @param holder
     * @param position
     */
    private void handleCallMessage(EMMessage message, ViewHolder holder,
                                   final int position) {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        holder.tv.setText(txtBody.getMessage());

    }

    /**
     * 图片消息
     *
     * @param message
     * @param holder
     * @param position
     * @param convertView
     */
    private void handleImageMessage(final EMMessage message,
                                    final ViewHolder holder, final int position, View convertView) {
        holder.pb.setTag(position);
        // holder.iv.setOnLongClickListener(new OnLongClickListener() {
        // @Override
        // public boolean onLongClick(View v) {
        // activity.startActivityForResult((new Intent(activity,
        // ContextMenu.class)).putExtra("position", position)
        // .putExtra("type", EMMessage.Type.IMAGE.ordinal()),
        // ChatActivity.REQUEST_CODE_CONTEXT_MENU);
        // return true;
        // }
        // });

        // 接收方向的消息
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            // "it is receive msg";
            if (message.status() == EMMessage.Status.INPROGRESS) {
                // "!!!! back receive";
                holder.iv.setImageResource(R.drawable.default_image);
                showDownloadImageProgress(message, holder);
                // downloadImage(message, holder);
            } else {
                // "!!!! not back receive, show image directly");
                holder.pb.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                holder.iv.setImageResource(R.drawable.default_image);
                EMImageMessageBody imgBody = (EMImageMessageBody) message.getBody();
                if (imgBody.getLocalUrl() != null) {
                    // String filePath = imgBody.getLocalUrl();
                    String remotePath = imgBody.getRemoteUrl();
                    String filePath = ImageUtils.getImagePath(remotePath);
                    String thumbRemoteUrl = imgBody.getThumbnailUrl();
                    String thumbnailPath = ImageUtils
                            .getThumbnailImagePath(thumbRemoteUrl);
                    showImageView(thumbnailPath, holder.iv, filePath,
                            imgBody.getRemoteUrl(), message);
                }
            }
            return;
        }

        // 发送的消息
        // process send message
        // send pic, show the pic directly
        EMImageMessageBody imgBody = (EMImageMessageBody) message.getBody();
        String filePath = imgBody.getLocalUrl();
        if (filePath != null && new File(filePath).exists()) {
            showImageView(ImageUtils.getThumbnailImagePath(filePath),
                    holder.iv, filePath, null, message);
        } else {
            showImageView(ImageUtils.getThumbnailImagePath(filePath),
                    holder.iv, filePath, IMAGE_DIR, message);
        }

        switch (message.status()) {
            case SUCCESS:
                holder.pb.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.GONE);
                break;
            case FAIL:
                holder.pb.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                holder.staus_iv.setVisibility(View.GONE);
                holder.pb.setVisibility(View.VISIBLE);
                holder.tv.setVisibility(View.VISIBLE);
                if (timers.containsKey(message.getMsgId()))
                    return;
                // set a timer
                final Timer timer = new Timer();
                timers.put(message.getMsgId(), timer);
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                holder.pb.setVisibility(View.VISIBLE);
                                holder.tv.setVisibility(View.VISIBLE);
                                holder.tv.setText(message.progress() + "%");
                                if (message.status() == EMMessage.Status.SUCCESS) {
                                    holder.pb.setVisibility(View.GONE);
                                    holder.tv.setVisibility(View.GONE);
                                    // message.setSendingStatus(Message.SENDING_STATUS_SUCCESS);
                                    timer.cancel();
                                } else if (message.status() == EMMessage.Status.FAIL) {
                                    holder.pb.setVisibility(View.GONE);
                                    holder.tv.setVisibility(View.GONE);
                                    // message.setSendingStatus(Message.SENDING_STATUS_FAIL);
                                    // message.setProgress(0);
                                    holder.staus_iv.setVisibility(View.VISIBLE);
                                    Toast.makeText(
                                            activity,
                                            activity.getString(R.string.send_fail)
                                                    + activity
                                                    .getString(R.string.connect_failuer_toast),
                                            Toast.LENGTH_SHORT).show();
                                    timer.cancel();
                                }

                            }
                        });

                    }
                }, 0, 500);
                break;
            default:
                sendPictureMessage(message, holder);
        }
    }

    /**
     * 视频消息
     *
     * @param message
     * @param holder
     * @param position
     * @param convertView
     */
    private void handleVideoMessage(final EMMessage message,
                                    final ViewHolder holder, final int position, View convertView) {

        EMVideoMessageBody videoBody = (EMVideoMessageBody) message.getBody();
        // final File image=new File(PathUtil.getInstance().getVideoPath(),
        // videoBody.getFileName());
        String localThumb = videoBody.getLocalThumb();

        // holder.iv.setOnLongClickListener(new OnLongClickListener() {
        //
        // @Override
        // public boolean onLongClick(View v) {
        // activity.startActivityForResult(new Intent(activity,
        // ContextMenu.class).putExtra("position", position)
        // .putExtra("type", EMMessage.Type.VIDEO.ordinal()),
        // ChatActivity.REQUEST_CODE_CONTEXT_MENU);
        // return true;
        // }
        // });

        if (localThumb != null) {

            showVideoThumbView(localThumb, holder.iv,
                    videoBody.getThumbnailUrl(), message);
        }
        if (videoBody.getDuration() > 0) {
            String time = DateUtils.toTimeBySecond(videoBody.getDuration());
            holder.timeLength.setText(time);
        }
        holder.playBtn.setImageResource(R.drawable.video_download_btn_nor);

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (videoBody.getVideoFileLength() > 0) {
                String size = TextFormater.getDataSize(videoBody
                        .getVideoFileLength());
                holder.size.setText(size);
            }
        } else {
            if (videoBody.getLocalUrl() != null
                    && new File(videoBody.getLocalUrl()).exists()) {
                String size = TextFormater.getDataSize(new File(videoBody
                        .getLocalUrl()).length());
                holder.size.setText(size);
            }
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {

            // System.err.println("it is receive msg");
            if (message.status() == EMMessage.Status.INPROGRESS) {
                // System.err.println("!!!! back receive");
                holder.iv.setImageResource(R.drawable.default_image);
                showDownloadImageProgress(message, holder);

            } else {
                // System.err.println("!!!! not back receive, show image directly");
                holder.iv.setImageResource(R.drawable.default_image);
                if (localThumb != null) {
                    showVideoThumbView(localThumb, holder.iv,
                            videoBody.getThumbnailUrl(), message);
                }

            }

            return;
        }
        holder.pb.setTag(position);

        // until here ,deal with send video msg
        switch (message.status()) {
            case SUCCESS:
                holder.pb.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                break;
            case FAIL:
                holder.pb.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                if (timers.containsKey(message.getMsgId()))
                    return;
                // set a timer
                final Timer timer = new Timer();
                timers.put(message.getMsgId(), timer);
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                holder.pb.setVisibility(View.VISIBLE);
                                holder.tv.setVisibility(View.VISIBLE);
                                holder.tv.setText(message.progress() + "%");
                                if (message.status() == EMMessage.Status.SUCCESS) {
                                    holder.pb.setVisibility(View.GONE);
                                    holder.tv.setVisibility(View.GONE);
                                    // message.setSendingStatus(Message.SENDING_STATUS_SUCCESS);
                                    timer.cancel();
                                } else if (message.status() == EMMessage.Status.FAIL) {
                                    holder.pb.setVisibility(View.GONE);
                                    holder.tv.setVisibility(View.GONE);
                                    // message.setSendingStatus(Message.SENDING_STATUS_FAIL);
                                    // message.setProgress(0);
                                    holder.staus_iv.setVisibility(View.VISIBLE);
                                    Toast.makeText(
                                            activity,
                                            activity.getString(R.string.send_fail)
                                                    + activity
                                                    .getString(R.string.connect_failuer_toast),
                                            Toast.LENGTH_SHORT).show();
                                    timer.cancel();
                                }

                            }
                        });

                    }
                }, 0, 500);
                break;
            default:
                // sendMsgInBackground(message, holder);
                sendPictureMessage(message, holder);

        }

    }

    /**
     * 语音消息
     *
     * @param message
     * @param holder
     * @param position
     * @param convertView
     */
    private void handleVoiceMessage(final EMMessage message,
                                    final ViewHolder holder, final int position, View convertView) {
        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) message.getBody();
        holder.tv.setText(voiceBody.getLength() + "\"");
        holder.iv.setOnClickListener(new VoicePlayClickListener(message,
                holder.iv, holder.iv_read_status, this, activity, username));
        // holder.iv.setOnLongClickListener(new OnLongClickListener() {
        // @Override
        // public boolean onLongClick(View v) {
        // activity.startActivityForResult((new Intent(activity,
        // ContextMenu.class)).putExtra("position", position)
        // .putExtra("type", EMMessage.Type.VOICE.ordinal()),
        // ChatActivity.REQUEST_CODE_CONTEXT_MENU);
        // return true;
        // }
        // });
        if (((ChatActivity) activity).playMsgId != null
                && ((ChatActivity) activity).playMsgId.equals(message
                .getMsgId()) && VoicePlayClickListener.isPlaying) {
            AnimationDrawable voiceAnimation;
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                holder.iv.setImageResource(R.anim.voice_from_icon);
            } else {
                holder.iv.setImageResource(R.anim.voice_to_icon);
            }
            voiceAnimation = (AnimationDrawable) holder.iv.getDrawable();
            voiceAnimation.start();
        } else {
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                holder.iv.setImageResource(R.drawable.chatfrom_voice_playing);
            } else {
                holder.iv.setImageResource(R.drawable.chatto_voice_playing);
            }
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (message.isListened()) {
                // 隐藏语音未听标志
                holder.iv_read_status.setVisibility(View.INVISIBLE);
            } else {
                holder.iv_read_status.setVisibility(View.VISIBLE);
            }
            System.err.println("it is receive msg");
            if (message.status() == EMMessage.Status.INPROGRESS) {
                holder.pb.setVisibility(View.VISIBLE);
                System.err.println("!!!! back receive");

                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                holder.pb.setVisibility(View.INVISIBLE);
                                notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                holder.pb.setVisibility(View.INVISIBLE);
                                notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            } else {
                holder.pb.setVisibility(View.INVISIBLE);

            }
            return;
        }

        // until here, deal with send voice msg
        switch (message.status()) {
            case SUCCESS:
                holder.pb.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.GONE);
                break;
            case FAIL:
                holder.pb.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                holder.pb.setVisibility(View.VISIBLE);
                holder.staus_iv.setVisibility(View.GONE);
                break;
            default:
                sendMsgInBackground(message, holder);
        }
    }

    /**
     * 文件消息
     *
     * @param message
     * @param holder
     * @param position
     * @param convertView
     */
    private void handleFileMessage(final EMMessage message,
                                   final ViewHolder holder, int position, View convertView) {
        final EMNormalFileMessageBody fileMessageBody = (EMNormalFileMessageBody) message
                .getBody();
        final String filePath = fileMessageBody.getLocalUrl();
        holder.tv_file_name.setText(fileMessageBody.getFileName());
        holder.tv_file_size.setText(TextFormater.getDataSize(fileMessageBody
                .getFileSize()));
        holder.ll_container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                File file = new File(filePath);
                if (file != null && file.exists()) {
                    // 文件存在，直接打开
                    FileUtils.openFile(file, (Activity) context);
                } else {
                    // 下载
                    context.startActivity(new Intent(context,
                            ShowNormalFileActivity.class).putExtra("msgbody",
                            fileMessageBody));
                }
                if (message.direct() == EMMessage.Direct.RECEIVE
                        && !message.isAcked()) {
                    try {
                        EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                        message.setAcked(true);
                    } catch (HyphenateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        if (message.direct() == EMMessage.Direct.RECEIVE) { // 接收的消息
            System.err.println("it is receive msg");
            File file = new File(filePath);
            if (file != null && file.exists()) {
                holder.tv_file_download_state.setText("已下载");
            } else {
                holder.tv_file_download_state.setText("未下载");
            }
            return;
        }

        // until here, deal with send voice msg
        switch (message.status()) {
            case SUCCESS:
                holder.pb.setVisibility(View.INVISIBLE);
                holder.tv.setVisibility(View.INVISIBLE);
                holder.staus_iv.setVisibility(View.INVISIBLE);
                break;
            case FAIL:
                holder.pb.setVisibility(View.INVISIBLE);
                holder.tv.setVisibility(View.INVISIBLE);
                holder.staus_iv.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                if (timers.containsKey(message.getMsgId()))
                    return;
                // set a timer
                final Timer timer = new Timer();
                timers.put(message.getMsgId(), timer);
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                holder.pb.setVisibility(View.VISIBLE);
                                holder.tv.setVisibility(View.VISIBLE);
                                holder.tv.setText(message.progress() + "%");
                                if (message.status() == EMMessage.Status.SUCCESS) {
                                    holder.pb.setVisibility(View.INVISIBLE);
                                    holder.tv.setVisibility(View.INVISIBLE);
                                    timer.cancel();
                                } else if (message.status() == EMMessage.Status.FAIL) {
                                    holder.pb.setVisibility(View.INVISIBLE);
                                    holder.tv.setVisibility(View.INVISIBLE);
                                    holder.staus_iv.setVisibility(View.VISIBLE);
                                    Toast.makeText(
                                            activity,
                                            activity.getString(R.string.send_fail)
                                                    + activity
                                                    .getString(R.string.connect_failuer_toast),
                                            Toast.LENGTH_SHORT).show();
                                    timer.cancel();
                                }

                            }
                        });

                    }
                }, 0, 500);
                break;
            default:
                // 发送消息
                sendMsgInBackground(message, holder);
        }

    }

    /**
     * 处理位置消息
     *
     * @param message
     * @param holder
     * @param position
     * @param convertView
     */
    private void handleLocationMessage(final EMMessage message,
                                       final ViewHolder holder, final int position, View convertView) {
        TextView locationView = ((TextView) convertView
                .findViewById(R.id.tv_location));
        EMLocationMessageBody locBody = (EMLocationMessageBody) message.getBody();
        locationView.setText(locBody.getAddress());
        LatLng loc = new LatLng(locBody.getLatitude(), locBody.getLongitude());
        locationView.setOnClickListener(new MapClickListener(loc, locBody
                .getAddress()));
        // locationView.setOnLongClickListener(new OnLongClickListener() {
        // @Override
        // public boolean onLongClick(View v) {
        // activity.startActivityForResult((new Intent(activity,
        // ContextMenu.class)).putExtra("position", position)
        // .putExtra("type", EMMessage.Type.LOCATION.ordinal()),
        // ChatActivity.REQUEST_CODE_CONTEXT_MENU);
        // return false;
        // }
        // });

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            return;
        }
        // deal with send message
        switch (message.status()) {
            case SUCCESS:
                holder.pb.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.GONE);
                break;
            case FAIL:
                holder.pb.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                holder.pb.setVisibility(View.VISIBLE);
                break;
            default:
                sendMsgInBackground(message, holder);
        }
    }

    /**
     * 发送消息
     *
     * @param message
     * @param holder
     */
    public void sendMsgInBackground(final EMMessage message,
                                    final ViewHolder holder) {
        holder.staus_iv.setVisibility(View.GONE);
        holder.pb.setVisibility(View.VISIBLE);

        final long start = System.currentTimeMillis();
        // 调用sdk发送异步发送方法
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                // umeng自定义事件，

                updateSendedView(message, holder);
            }

            @Override
            public void onError(int i, String s) {
                updateSendedView(message, holder);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
       EMClient.getInstance().chatManager().sendMessage(message);



    }

    /*
     * chat sdk will automatic download thumbnail image for the image message we
     * need to register callback show the download progress
     */
    private void showDownloadImageProgress(final EMMessage message,
                                           final ViewHolder holder) {
        System.err.println("!!! show download image progress");
        // final ImageMessageBody msgbody = (ImageMessageBody)
        // message.getBody();
        final EMFileMessageBody  msgbody = (EMFileMessageBody) message.getBody();
        if (holder.pb != null)
            holder.pb.setVisibility(View.VISIBLE);
        if (holder.tv != null)
            holder.tv.setVisibility(View.VISIBLE);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // message.setBackReceive(false);
                        if (message.getType() == EMMessage.Type.IMAGE) {
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                        }
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(final int progress, String status) {
                if (message.getType() == EMMessage.Type.IMAGE) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.tv.setText(progress + "%");

                        }
                    });
                }
            }
        });
    }

    /*
     * send message with new sdk
     */
    private void sendPictureMessage(final EMMessage message,
                                    final ViewHolder holder) {
        try {
            String to = message.getTo();

            // before send, update ui
            holder.staus_iv.setVisibility(View.GONE);
            holder.pb.setVisibility(View.VISIBLE);
            holder.tv.setVisibility(View.VISIBLE);
            holder.tv.setText("0%");

            final long start = System.currentTimeMillis();
            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            // send success
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                            // message.setSendingStatus(Message.SENDING_STATUS_FAIL);
                            holder.staus_iv.setVisibility(View.VISIBLE);
                            Toast.makeText(
                                    activity,
                                    activity.getString(R.string.send_fail)
                                            + activity
                                            .getString(R.string.connect_failuer_toast),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onProgress(final int progress, String s) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            holder.tv.setText(progress + "%");
                        }
                    });
                }
            });
            EMClient.getInstance().chatManager().sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新ui上消息发送状态
     *
     * @param message
     * @param holder
     */
    private void updateSendedView(final EMMessage message,
                                  final ViewHolder holder) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // send success
                if (message.getType() == EMMessage.Type.VIDEO) {
                    holder.tv.setVisibility(View.GONE);
                }
                if (message.status() == EMMessage.Status.SUCCESS) {
                    // if (message.getType() == EMMessage.Type.FILE) {
                    // holder.pb.setVisibility(View.INVISIBLE);
                    // holder.staus_iv.setVisibility(View.INVISIBLE);
                    // } else {
                    // holder.pb.setVisibility(View.GONE);
                    // holder.staus_iv.setVisibility(View.GONE);
                    // }

                } else if (message.status() == EMMessage.Status.FAIL) {
                    // if (message.getType() == EMMessage.Type.FILE) {
                    // holder.pb.setVisibility(View.INVISIBLE);
                    // } else {
                    // holder.pb.setVisibility(View.GONE);
                    // }
                    // holder.staus_iv.setVisibility(View.VISIBLE);
                    Toast.makeText(
                            activity,
                            activity.getString(R.string.send_fail)
                                    + activity
                                    .getString(R.string.connect_failuer_toast),
                            Toast.LENGTH_LONG).show();
                }

                notifyDataSetChanged();
            }
        });
    }

    /**
     * load image into image view
     *
     * @param thumbernailPath
     * @param iv
     * @return the image exists or not
     */
    private boolean showImageView(final String thumbernailPath,
                                  final ImageView iv, final String localFullSizePath,
                                  String remoteDir, final EMMessage message) {
        // String imagename =
        // localFullSizePath.substring(localFullSizePath.lastIndexOf("/") + 1,
        // localFullSizePath.length());
        // final String remote = remoteDir != null ? remoteDir+imagename :
        // imagename;
        final String remote = remoteDir;
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = ImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            iv.setImageBitmap(bitmap);
            iv.setClickable(true);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.err.println("image view on click");
                    Intent intent = new Intent(activity, ShowBigImage.class);
                    File file = new File(localFullSizePath);
                    if (file.exists()) {
                        Uri uri = Uri.fromFile(file);
                        intent.putExtra("uri", uri);
                        System.err
                                .println("here need to check why download everytime");
                    } else {
                        // The local full size pic does not exist yet.
                        // ShowBigImage needs to download it from the server
                        // first
                        // intent.putExtra("", message.get);
                        EMImageMessageBody body = (EMImageMessageBody) message
                                .getBody();
                        intent.putExtra("secret", body.getSecret());
                        intent.putExtra("remotepath", remote);
                    }
                    if (message != null
                            && message.direct() == EMMessage.Direct.RECEIVE
                            && !message.isAcked()
                            && message.getChatType() != EMMessage.ChatType.GroupChat) {
                        try {

                            EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                            message.setAcked(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    activity.startActivity(intent);
                }
            });
            return true;
        } else {

            new LoadImageTask().execute(thumbernailPath, localFullSizePath,
                    remote, message.getChatType(), iv, activity, message);
            return true;
        }

    }

    /**
     * 展示视频缩略图
     *
     * @param localThumb
     *            本地缩略图路径
     * @param iv
     * @param thumbnailUrl
     *            远程缩略图路径
     * @param message
     */
    private void showVideoThumbView(String localThumb, ImageView iv,
                                    String thumbnailUrl, final EMMessage message) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = ImageCache.getInstance().get(localThumb);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            iv.setImageBitmap(bitmap);
            iv.setClickable(true);
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    EMVideoMessageBody videoBody = (EMVideoMessageBody) message
                            .getBody();
                    System.err.println("video view is on click");
                    Intent intent = new Intent(activity,
                            ShowVideoActivity.class);
                    intent.putExtra("localpath", videoBody.getLocalUrl());
                    intent.putExtra("secret", videoBody.getSecret());
                    intent.putExtra("remotepath", videoBody.getRemoteUrl());
                    if (message != null
                            && message.direct() == EMMessage.Direct.RECEIVE
                            && !message.isAcked()
                            && message.getChatType() != EMMessage.ChatType.GroupChat) {
                        message.setAcked(true);
                        try {
                            EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    activity.startActivity(intent);

                }
            });

        } else {
            new LoadVideoImageTask().execute(localThumb, thumbnailUrl, iv,
                    activity, message, this);
        }

    }

    public static class ViewHolder {
        ImageView iv;
        TextView tv;
        ProgressBar pb;
        ImageView staus_iv;
        ImageView head_iv;
        TextView tv_userId;
        ImageView playBtn;
        TextView timeLength;
        TextView size;
        LinearLayout container_status_btn;
        LinearLayout ll_container;
        ImageView iv_read_status;
        // 显示已读回执状态
        TextView tv_ack;
        // 显示送达回执状态
        TextView tv_delivered;

        TextView tv_file_name;
        TextView tv_file_size;
        TextView tv_file_download_state;
    }

    /*
     * 点击地图消息listener
     */
    class MapClickListener implements View.OnClickListener {

        LatLng location;
        String address;

        public MapClickListener(LatLng loc, String address) {
            location = loc;
            this.address = address;

        }

        @Override
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(context, BaiduMapActivity.class);
            intent.putExtra("latitude", location.latitude);
            intent.putExtra("longitude", location.longitude);
            intent.putExtra("address", address);
            activity.startActivity(intent);
        }

    }




}
