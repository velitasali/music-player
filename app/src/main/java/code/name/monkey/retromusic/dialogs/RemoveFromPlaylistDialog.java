package code.name.monkey.retromusic.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.velitasali.music.R;

import java.util.ArrayList;

import code.name.monkey.backend.model.PlaylistSong;
import code.name.monkey.retromusic.util.PlaylistsUtil;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class RemoveFromPlaylistDialog extends DialogFragment
{

	@NonNull
	public static RemoveFromPlaylistDialog create(PlaylistSong song)
	{
		ArrayList<PlaylistSong> list = new ArrayList<>();
		list.add(song);
		return create(list);
	}

	@NonNull
	public static RemoveFromPlaylistDialog create(ArrayList<PlaylistSong> songs)
	{
		RemoveFromPlaylistDialog dialog = new RemoveFromPlaylistDialog();
		Bundle args = new Bundle();
		args.putParcelableArrayList("songs", songs);
		dialog.setArguments(args);
		return dialog;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		//noinspection unchecked
		final ArrayList<PlaylistSong> songs = getArguments().getParcelableArrayList("songs");
		int title;
		CharSequence content;
		if (songs.size() > 1) {
			title = R.string.remove_songs_from_playlist_title;
			content = Html.fromHtml(getString(R.string.remove_x_songs_from_playlist, songs.size()));
		} else {
			title = R.string.remove_song_from_playlist_title;
			content = Html.fromHtml(getString(R.string.remove_song_x_from_playlist, songs.get(0).title));
		}
		return new MaterialDialog.Builder(getActivity())
				.title(title)
				.content(content)
				.positiveText(R.string.remove_action)
				.negativeText(android.R.string.cancel)
				.onPositive(new MaterialDialog.SingleButtonCallback()
				{
					@Override
					public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
					{
						if (getActivity() == null)
							return;
						PlaylistsUtil.removeFromPlaylist(getActivity(), songs);
					}
				})
				.build();
	}
}