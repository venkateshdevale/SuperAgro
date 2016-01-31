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
import android.widget.TextView;
import android.widget.Toast;

import com.weloftlabs.superagro.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
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


    private ComboLineColumnChartView chart2;
    private ComboLineColumnChartData data2;

    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasPoints = true;
    private boolean hasLines = true;
    private boolean isCubic = false;


        View rootView;
        private TextView textView;

        public PieChartFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            rootView = inflater.inflate(R.layout.fragment_pie_chart, container, false);

            chart = (PieChartView) rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());
            hasCenterText1 = true;
            textView = (TextView) rootView.findViewById(R.id.crop_name1);

            chart2 = (ComboLineColumnChartView) rootView.findViewById(R.id.chart2);
            chart2.setOnValueTouchListener(new ValueTouchListener2());

            generateData();
            //explodeChart();
            prepareDataAnimation();
            chart.startDataAnimation();



            generateValues();
            generateData2();

            prepareDataAnimation2();
            chart2.startDataAnimation();


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
                        textView.setText("Rice will yield 1500$ extra money than last year per acre. We suggest you RICE as the Best crop for the year 2016.");
                        Snackbar.make(rootView, "Rice  :: 1500$ extra than last year. Best for year 2016.", Snackbar.LENGTH_SHORT).show();

                        resetChart2();
                        generateValues();
                        generateData2();
                        prepareDataAnimation2();
                        chart2.startDataAnimation();
                        break;
                    case 1:
                        textView.setText("Wheat will yield 10% more yield than last year per acre. We suggest you this as the next option after rice for the year 2016.");
                        Snackbar.make(rootView, "Wheat  :: 10% more yield than last year", Snackbar.LENGTH_SHORT).show();

                        resetChart2();
                        generateValues();
                        generateData2();
                        prepareDataAnimation2();
                        chart2.startDataAnimation();
                        break;
                    case 2:
                        textView.setText("Soya will yield 20% more yield than last year per acre. We suggest you this as the third option after wheat for the year 2016.");
                        Snackbar.make(rootView, "Soya  :: 20% more yield than last year", Snackbar.LENGTH_SHORT).show();

                        resetChart2();
                        generateValues();
                        generateData2();
                        prepareDataAnimation2();
                        chart2.startDataAnimation();
                        break;
                    case 3:
                        textView.setText("Peanuts will yield more crops than last year per acre. We suggest you this as the fourth option based on our analysis on the previous year data. Go for it if you don't have other better options.");
                        Snackbar.make(rootView, "Peanuts :: more yield expected", Snackbar.LENGTH_SHORT).show();

                        resetChart2();
                        generateValues();
                        generateData2();
                        prepareDataAnimation2();
                        chart2.startDataAnimation();
                        break;
                    case 4:
                        textView.setText("Tobacco will yield more profit than last year per acre. We suggest you this as the fourth option based on our analysis on the previous year data. Go for it if you don't have other better options.");
                        Snackbar.make(rootView, "Tobacco :: more profit expected", Snackbar.LENGTH_SHORT).show();

                        resetChart2();
                        generateValues();
                        generateData2();
                        prepareDataAnimation2();
                        chart2.startDataAnimation();
                        break;
                    case 5:
                        textView.setText("Cotton will yield more profit than last year per acre. We suggest you this as the fourth option based on our analysis on the previous year data. Go for it if you don't have other better options.");
                        Snackbar.make(rootView, "Cotton :: more profit expected", Snackbar.LENGTH_SHORT).show();

                        resetChart2();
                        generateValues();
                        generateData2();
                        prepareDataAnimation2();
                        chart2.startDataAnimation();
                        break;

                }




            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }




















    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 50f + 5;
            }
        }
    }

    private void resetChart2() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        hasLabels = false;
        isCubic = false;

    }

    private void generateData2() {
        // Chart looks the best when line data and column data have similar maximum viewports.
        data2 = new ComboLineColumnChartData(generateColumnData(), generateLineData());

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("No of acres");
                axisY.setName("Tons");
            }
            data2.setAxisXBottom(axisX);
            data2.setAxisYLeft(axisY);
        } else {
            data2.setAxisXBottom(null);
            data2.setAxisYLeft(null);
        }

        chart2.setComboLineColumnChartData(data2);
    }

    private LineChartData generateLineData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setCubic(isCubic);
            line.setHasLabels(true);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }

    private ColumnChartData generateColumnData() {
        int numSubcolumns = 1;
        int numColumns = 12;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50 + 5, ChartUtils.COLOR_GREEN));
            }

            columns.add(new Column(values));
        }

        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }

    private void addLineToData() {
        if (data2.getLineChartData().getLines().size() >= maxNumberOfLines) {
            //Toast.makeText(getActivity(), "Samples app uses max 4 lines!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ++numberOfLines;
        }

        generateData();
    }

    private void toggleLines() {
        hasLines = !hasLines;

        generateData();
    }

    private void togglePoints() {
        hasPoints = !hasPoints;

        generateData();
    }

    private void toggleCubic() {
        isCubic = !isCubic;

        generateData();
    }

    private void toggleLabels2() {
        hasLabels = !hasLabels;

        generateData();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }

    private void prepareDataAnimation2() {

        // Line animations
        for (Line line : data2.getLineChartData().getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 50 + 5);
            }
        }

        // Columns animations
        for (Column column : data2.getColumnChartData().getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget((float) Math.random() * 50 + 5);
            }
        }
    }

private class ValueTouchListener2 implements ComboLineColumnChartOnValueSelectListener {

    @Override
    public void onValueDeselected() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {

        switch(columnIndex)
        {
            case 0:

                //textView.setText("Rice will yield 1500$ extra money than last year per acre. We suggest you RICE as the Best crop for the year 2016.");
                Snackbar.make(rootView, "Rice  :: 1500$ extra than last year. Best for year 2016.", Snackbar.LENGTH_SHORT).show();
                break;
            case 1:
               // textView.setText("Wheat will yield 10% more yield than last year per acre. We suggest you this as the next option after rice for the year 2016.");
                Snackbar.make(rootView, "Wheat  :: 10% more yield than last year", Snackbar.LENGTH_SHORT).show();
                break;
            case 2:
               // textView.setText("Soya will yield 20% more yield than last year per acre. We suggest you this as the third option after wheat for the year 2016.");
                Snackbar.make(rootView, "Soya  :: 20% more yield than last year", Snackbar.LENGTH_SHORT).show();
                break;
            case 3:
               // textView.setText("Peanuts will yield more crops than last year per acre. We suggest you this as the fourth option based on our analysis on the previous year data. Go for it if you don't have other better options.");
                Snackbar.make(rootView, "Peanuts :: more yield expected", Snackbar.LENGTH_SHORT).show();
                break;
            case 4:
               // textView.setText("Tobacco will yield more profit than last year per acre. We suggest you this as the fourth option based on our analysis on the previous year data. Go for it if you don't have other better options.");
                Snackbar.make(rootView, "Tobacco :: more profit expected", Snackbar.LENGTH_SHORT).show();
                break;
            case 5:
               // textView.setText("Cotton will yield more profit than last year per acre. We suggest you this as the fourth option based on our analysis on the previous year data. Go for it if you don't have other better options.");
                Snackbar.make(rootView, "Cotton :: more profit expected", Snackbar.LENGTH_SHORT).show();
                break;

        }    }

    @Override
    public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
        Toast.makeText(getActivity(), "Selected line point: " + value, Toast.LENGTH_SHORT).show();
    }

}


}
