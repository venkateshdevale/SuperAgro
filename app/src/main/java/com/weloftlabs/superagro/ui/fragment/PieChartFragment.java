package com.weloftlabs.superagro.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.weloftlabs.superagro.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by stom on 31/01/16.
 */
public class PieChartFragment  extends Fragment {

        private PieChartView chart;
        private PieChartData data;

        private boolean hasLabels = false;
        private boolean hasLabelsOutside = false;
        private boolean hasCenterCircle = false;
        private boolean hasCenterText1 = false;
        private boolean hasCenterText2 = false;
        private boolean isExploded = false;
        private boolean hasLabelForSelected = false;
        View rootView;
        public PieChartFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            rootView = inflater.inflate(R.layout.fragment_pie_chart, container, false);

            chart = (PieChartView) rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());
            hasCenterText1 = true;

            generateData();
            //explodeChart();
            prepareDataAnimation();
            chart.startDataAnimation();

            return rootView;
        }

        // MENU
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.pie_chart, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_reset) {
                reset();
                generateData();
                return true;
            }
            if (id == R.id.action_explode) {
                explodeChart();
                return true;
            }
            if (id == R.id.action_center_circle) {
                hasCenterCircle = !hasCenterCircle;
                if (!hasCenterCircle) {
                    hasCenterText1 = false;
                    hasCenterText2 = false;
                }

                generateData();
                return true;
            }
            if (id == R.id.action_center_text1) {
                hasCenterText1 = !hasCenterText1;

                if (hasCenterText1) {
                    hasCenterCircle = true;
                }

                hasCenterText2 = false;

                generateData();
                return true;
            }
            if (id == R.id.action_center_text2) {
                hasCenterText2 = !hasCenterText2;

                if (hasCenterText2) {
                    hasCenterText1 = true;// text 2 need text 1 to by also drawn.
                    hasCenterCircle = true;
                }

                generateData();
                return true;
            }
            if (id == R.id.action_toggle_labels) {
                toggleLabels();
                return true;
            }
            if (id == R.id.action_toggle_labels_outside) {
                toggleLabelsOutside();
                return true;
            }
            if (id == R.id.action_animate) {
                prepareDataAnimation();
                chart.startDataAnimation();
                return true;
            }
            if (id == R.id.action_toggle_selection_mode) {
                toggleLabelForSelected();
                Toast.makeText(getActivity(),
                        "Selection mode set to " + chart.isValueSelectionEnabled() + " select any point.",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void reset() {
            chart.setCircleFillRatio(1.0f);
            hasLabels = true;
            hasLabelsOutside = false;
            hasCenterCircle = true;
            hasCenterText1 = true;
            hasCenterText2 = false;
            isExploded = false;
            hasLabelForSelected = false;
        }

        private void generateData() {
            int numValues = 6;

            List<SliceValue> values = new ArrayList<SliceValue>();
            for (int i = 0; i < numValues; ++i) {
                SliceValue sliceValue = new SliceValue((float) Math.random() * 10 + 15, ChartUtils.pickColor());
                sliceValue.setLabel("yo");
                //sliceValue.set
                values.add(sliceValue);
            }

            data = new PieChartData(values);
            data.setHasLabels(hasLabels);
            //data.set
            data.setHasLabelsOnlyForSelected(hasLabelForSelected);
            data.setHasLabelsOutside(hasLabelsOutside);
            data.setHasCenterCircle(hasCenterCircle);
            data.setCenterText1("hello");
            data.setValueLabelBackgroundAuto(true);
            data.setValueLabelBackgroundEnabled(true);
            data.setValueLabelBackgroundColor(getActivity().getResources().getColor(android.R.color.black));
            data.setValueLabelsTextColor(getActivity().getResources().getColor(android.R.color.white));

            if (isExploded) {
                data.setSlicesSpacing(14);
            }

            if (hasCenterText1) {
                data.setCenterText1("Top Crops");

                // Get roboto-italic font.
                //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
                //data.setCenterText1Typeface(tf);

                // Get font size from dimens.xml and convert it to sp(library uses sp values).
                data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                        (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
            }

            if (hasCenterText2) {
                data.setCenterText2("Charts (Roboto Italic)");

                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");

                data.setCenterText2Typeface(tf);
                data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                        (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
            }

            chart.setPieChartData(data);
        }

        private void explodeChart() {
            isExploded = !isExploded;
            generateData();

        }

        private void toggleLabelsOutside() {
            // has labels have to be true:P
            hasLabelsOutside = !hasLabelsOutside;
            if (hasLabelsOutside) {
                hasLabels = true;
                hasLabelForSelected = false;
                chart.setValueSelectionEnabled(hasLabelForSelected);
            }

            if (hasLabelsOutside) {
                chart.setCircleFillRatio(0.7f);
            } else {
                chart.setCircleFillRatio(1.0f);
            }

            generateData();

        }

        private void toggleLabels() {
            hasLabels = !hasLabels;

            if (hasLabels) {
                hasLabelForSelected = false;
                chart.setValueSelectionEnabled(hasLabelForSelected);

                if (hasLabelsOutside) {
                    chart.setCircleFillRatio(0.7f);
                } else {
                    chart.setCircleFillRatio(1.0f);
                }
            }

            generateData();
        }

        private void toggleLabelForSelected() {
            hasLabelForSelected = !hasLabelForSelected;

            chart.setValueSelectionEnabled(hasLabelForSelected);

            if (hasLabelForSelected) {
                hasLabels = false;
                hasLabelsOutside = false;

                if (hasLabelsOutside) {
                    chart.setCircleFillRatio(0.7f);
                } else {
                    chart.setCircleFillRatio(1.0f);
                }
            }

            generateData();
        }

        /**
         * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
         * method(don't confuse with View.animate()).
         */
        private void prepareDataAnimation() {
            for (SliceValue value : data.getValues()) {
                value.setTarget((float) Math.random() * 30 + 15);
            }
        }

        private class ValueTouchListener implements PieChartOnValueSelectListener {

            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                //Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();

                switch(arcIndex)
                {
                    case 0:
                        Snackbar.make(rootView, "Rice  :: 1500$ extra than last year. Best for year 2016.", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Snackbar.make(rootView, "Wheat  :: 10% more yield than last year", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Snackbar.make(rootView, "Soya  :: 20% more yield than last year", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Snackbar.make(rootView, "Peanuts :: more yield expected", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Snackbar.make(rootView, "Tobacco :: more profit expected", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Snackbar.make(rootView, "Cotton :: more profit expected", Snackbar.LENGTH_SHORT).show();
                        break;

                }




            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }

}
