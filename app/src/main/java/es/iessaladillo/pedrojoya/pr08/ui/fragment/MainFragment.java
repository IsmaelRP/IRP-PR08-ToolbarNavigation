package es.iessaladillo.pedrojoya.pr08.ui.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr08.R;
import es.iessaladillo.pedrojoya.pr08.databinding.FragmentMainBinding;
import es.iessaladillo.pedrojoya.pr08.ui.activity.MainViewModel;
import es.iessaladillo.pedrojoya.pr08.ui.activity.Navigations;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {


    private FragmentMainBinding b;
    private Navigate listener;
    private MainViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return b.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (Navigate) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " debe implementar la interfaz Navigate del fragmento");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        setupObserver();
        setupViews();
    }

    private void setupObserver() {
        vm.getLoremType().observe(this, this::changeLorem);
    }

    private void changeLorem(String type) {
        final String LATIN = "lat";
        final String CHIQUITO = "chiq";

        if (type.equals(LATIN)) {
            b.txtLorem1.setText(getString(R.string.main_latin_ipsum));
        } else if (type.equals(CHIQUITO)) {
            b.txtLorem1.setText(getString(R.string.main_chiquito_ipsum));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSettings:
                listener.navigateTo(Navigations.SETTINGS);
                break;
        }
        return true;
    }

    private void setupViews() {
        b.fab1.setOnClickListener(v -> listener.navigateTo(Navigations.SECOND));
        ((AppCompatActivity) requireActivity()).setSupportActionBar(b.toolbar1);
        if (vm.getLoremType().getValue() != null) {


            if (vm.getLoremType().getValue().equals(getString(R.string.loremDefault))) {
                b.txtLorem1.setText(getString(R.string.main_latin_ipsum));
            } else {
                b.txtLorem1.setText(getString(R.string.main_chiquito_ipsum));
            }
        }
    }
public interface Navigate {
    void navigateTo(Navigations navigations);
}

}
