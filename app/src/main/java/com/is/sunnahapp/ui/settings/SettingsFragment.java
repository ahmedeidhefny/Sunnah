package com.is.sunnahapp.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.is.sunnahapp.R;
import com.is.sunnahapp.data.binding.FragmentDataBindingComponent;
import com.is.sunnahapp.databinding.FragmentDetailsBinding;
import com.is.sunnahapp.ui.base.BaseFragment;
import com.is.sunnahapp.ui.base.DataBoundListAdapter;
import com.is.sunnahapp.util.AutoClearedValue;
import com.is.sunnahapp.util.Utils;

import java.util.Locale;

/**
 * @author Ahmed Eid Hefny
 * @date 15/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class SettingsFragment extends BaseFragment implements DataBoundListAdapter.DiffUtilDispatchedInterface {

    //region Variables
    //SharedPreferences.Editor editor;
    //SharedPreferences sharedPreferences;

    private final androidx.databinding.DataBindingComponent dataBindingComponent =
            new FragmentDataBindingComponent(this);

    @VisibleForTesting
    private AutoClearedValue<FragmentDetailsBinding> binding;

    private String country;

    //endregion

    //region Constructor

    public SettingsFragment() {
        // Required empty public constructor
    }

    //endregion

    //region Overrides
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDetailsBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        return binding.get().getRoot();
    }

    @Override
    public void onDispatched() {
    }

    @Override
    protected void initUIAndActions() {
        //editor = getActivity().getSharedPreferences(SP_SETTINGS_NAME, getActivity().MODE_PRIVATE).edit();
        //sharedPreferences = getActivity().getSharedPreferences(SP_SETTINGS_NAME, getActivity().MODE_PRIVATE);

        selectLanguage();

        fontSize();
    }

    @Override
    protected void initViewModels() {

    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {

        loadData();

        try {
            Utils.log(">>>> On initData.");
        } catch (NullPointerException ne) {
            Utils.errorLog("Null Pointer Exception.", ne);
        } catch (Exception e) {
            Utils.errorLog("Error in getting notification flag data.", e);
        }
    }

    private void selectLanguage() {
        binding.get().selectLanguageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] items = new CharSequence[]{
                        getString(R.string.english_language),
                        getString(R.string.arabic_language),
                        getString(R.string.both_language)
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.choose_language));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            setLocate("en");
                        } else if (i == 1) {
                            setLocate("ar");
                        } else if (i == 2) {
                            setLocate("both");
                        }

                    }
                });
                builder.show();
            }
        });
    }

    private void fontSize() {
        binding.get().fontSizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] items = getActivity().getResources().getStringArray(R.array.font_size);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.choose_font));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int fontSize = Integer.parseInt((String) items[i]);
                        changeFontSize(fontSize);
                    }
                });
                builder.show();
            }

        });
    }

    private void changeFontSize(int fontSize) {
//        editor.putInt(Constants.SP_FONT_SIZE, fontSize);
//        editor.apply();
        //Toast.makeText(getActivity(), ""+fontSize, Toast.LENGTH_SHORT).show();
        pref.setFontSize(fontSize);
        getActivity().recreate();
    }

    private void setLocate(String lang) {
        if (lang.equalsIgnoreCase("both")) {
            setLocalization("en");
        } else {
            setLocalization(lang);
        }

        saveStateToLocal(lang);
    }

    private void setLocalization(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseActivity().getResources().updateConfiguration(configuration, getBaseActivity().getResources().getDisplayMetrics());

    }

    private void saveStateToLocal(String lang) {
//        editor.putString(Constants.SP_LANGUAGE, lang);
//        editor.apply();

        pref.saveLang(lang);
        navigationController.navigateToSplashActivity(getActivity());
    }

    //endregion

    //region Private Methods

    private void loadData() {
        getLanguage();
        getFontSize();

        //Toast.makeText(getActivity(), "" + lang, Toast.LENGTH_SHORT).show();
    }

    private void getFontSize() {
        //int fontSize = sharedPreferences.getInt(Constants.SP_FONT_SIZE, 16);
        int fontSize = pref.getFontSize();
        if (fontSize == 16) {
            binding.get().fontSizeValue.setText(fontSize + "sp Default");
        } else {
            binding.get().fontSizeValue.setText(fontSize + "sp");
        }
        binding.get().fontSizeValue.setTextSize(fontSize);
        binding.get().languageValue.setTextSize(fontSize);
        binding.get().settingsTxt.setTextSize(fontSize + 2);
        binding.get().selectLangTxt.setTextSize(fontSize);
        binding.get().fontSizeTxt.setTextSize(fontSize);
    }

    private void getLanguage() {
        //String lang = sharedPreferences.getString(Constants.SP_LANGUAGE, "");
        String lang = pref.getLang();
        if (lang.equalsIgnoreCase("ar")) {
            binding.get().languageValue.setText(getString(R.string.arabic_language));
        } else if (lang.equalsIgnoreCase("en")) {
            binding.get().languageValue.setText(getString(R.string.english_language));
        } else {
            binding.get().languageValue.setText(getString(R.string.both2_language));
        }
    }

    //endregion

}
