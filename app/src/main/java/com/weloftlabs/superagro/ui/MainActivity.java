package com.weloftlabs.superagro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.weloftlabs.superagro.CropDetailActivity;
import com.weloftlabs.superagro.R;
import com.weloftlabs.superagro.adapter.ViewPagerAdapter;
import com.weloftlabs.superagro.ui.fragment.CommonCropsCardListFragment;
import com.weloftlabs.superagro.ui.fragment.PieChartFragment;
import com.weloftlabs.superagro.util.GlobalConstant;

public class MainActivity extends AppCompatActivity {

    public Drawer result;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    public static int selectOption = 0;
    public static String selectedText = "";
    private Bundle bundle;
    private View parentLayout;
    private String expenseType;
    Button detailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalConstant.mContext = MainActivity.this;
        parentLayout = findViewById(R.id.root_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        detailsButton = (Button) findViewById(R.id.details_button);
        detailsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,CropDetailActivity.class));
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.home_icon);
        PrimaryDrawerItem productBasedAnalysisDrawerItem = new PrimaryDrawerItem().withName(R.string.nav_item_product_based_analysis).withIcon(R.drawable.crop_based_analysis);
        PrimaryDrawerItem stateBasedDrawerItem = new PrimaryDrawerItem().withName(R.string.nav_item_state_based_analysis).withIcon(R.drawable.state_based_analysis);
        PrimaryDrawerItem compareBasedDrawerItem = new PrimaryDrawerItem().withName(R.string.nav_item_compare_crops_analysis).withIcon(R.drawable.compare_drawer);

        SectionDrawerItem productionAnalysisDrawerItem = new SectionDrawerItem().withName(R.string.nav_item_compare_production_analysis).withSelectable(false);
        SectionDrawerItem expensesAnalysisDrawerItem = new SectionDrawerItem().withName(R.string.nav_item_expenses_analysis).withSelectable(false);

        PrimaryDrawerItem stateProductivity = new PrimaryDrawerItem().withName(R.string.nav_sub_item_production_state_analysis).withIcon(R.drawable.state_productivity);
        PrimaryDrawerItem cropProductivity = new PrimaryDrawerItem().withName(R.string.nav_sub_item_production_crop_analysis).withIcon(R.drawable.crop_productivity);

        PrimaryDrawerItem totalOperationExpenses = new PrimaryDrawerItem().withName(R.string.nav_sub_item_total_operation_analysis).withIcon(R.drawable.expense_icon);
        PrimaryDrawerItem fertilizerExpenses = new PrimaryDrawerItem().withName(R.string.nav_sub_item_fertilizer_analysis).withIcon(R.drawable.price);
        PrimaryDrawerItem chemicalExpenses = new PrimaryDrawerItem().withName(R.string.nav_sub_item_chemical_analysis).withIcon(R.drawable.price);
        PrimaryDrawerItem laborExpenses = new PrimaryDrawerItem().withName(R.string.nav_sub_item_labor_analysis).withIcon(R.drawable.price);

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.header)
                .withSelectedItem(-1)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        homeDrawerItem, //Item 1
                        new DividerDrawerItem(), //Item 2
                        compareBasedDrawerItem, //Item 3
                        stateBasedDrawerItem,  //Item 4
                        productBasedAnalysisDrawerItem,  //Item 5
                        productionAnalysisDrawerItem, //Item 6
                        stateProductivity,  //Item 7
                       cropProductivity
                        //,  //Item 8
//                        expensesAnalysisDrawerItem, //Item 9
//                        totalOperationExpenses, //Item 10
//                        fertilizerExpenses, //Item 11
//                        chemicalExpenses, //Item 12
//                        laborExpenses //Item 13
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                        } else if (position == 3) {
                            compareActivity();
                        } else if (position == 4) {
                            stateBasedAnalysis();
                        } else if (position == 5) {
                            cropBasedAnalysis();
                        } else if (position == 7) {
                            productionStateAnalysis();
                        } else if (position == 8) {
                            productionCropAnalysis();
                        } else if (position == 10) {
                            expenseType = GlobalConstant.TAG_TOTAL_OPERATION_EXPENSES_TYPE;
                            chooseStateExpenseAnalysis();
                        } else if (position == 11) {
                            expenseType = GlobalConstant.TAG_FERTILIZER_EXPENSES_TYPE;
                            chooseStateExpenseAnalysis();
                        } else if (position == 12) {
                            expenseType = GlobalConstant.TAG_CHEMICAL_EXPENSES_TYPE;
                            chooseStateExpenseAnalysis();
                        } else if (position == 13) {
                            expenseType = GlobalConstant.TAG_LABOR_EXPENSES_TYPE;
                            chooseStateExpenseAnalysis();
                        }
                        return false;
                    }
                })
                .withSelectedItemByPosition(1)
                .build();

        Snackbar.make(parentLayout, "You will see the most valuable crops analysis acroos all states. Please wait for a while..", Snackbar.LENGTH_LONG).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Fragment pieChartFragment  = new PieChartFragment();
        adapter.addFragment(pieChartFragment, "Home");

        bundle = new Bundle();
        bundle.putString(GlobalConstant.TAG_YEAR, "" + GlobalConstant.getPreviousYear());
        bundle.putString(GlobalConstant.TAG_ANALYSIS_TYPE, "YIELD");
        bundle.putString(GlobalConstant.TAG_STATE_NAME, "");
        bundle.putString(GlobalConstant.TAG_CROP_NAME, "");
        bundle.putString(GlobalConstant.TAG_isShowLoadingDialog, GlobalConstant.TAG_NO);
        Fragment fragmentYield = new CommonCropsCardListFragment();
        fragmentYield.setArguments(bundle);
        adapter.addFragment(fragmentYield, "Yield");






        bundle = new Bundle();
        bundle.putString(GlobalConstant.TAG_YEAR, "" + GlobalConstant.getPreviousYear());
        bundle.putString(GlobalConstant.TAG_ANALYSIS_TYPE, "PRICE RECEIVED");
        bundle.putString(GlobalConstant.TAG_STATE_NAME, "");
        bundle.putString(GlobalConstant.TAG_CROP_NAME, "");
        bundle.putString(GlobalConstant.TAG_isShowLoadingDialog, GlobalConstant.TAG_NO);
        Fragment fragmentPriceReceived = new CommonCropsCardListFragment();
        fragmentPriceReceived.setArguments(bundle);
        adapter.addFragment(fragmentPriceReceived, "PRICE RECEIVED");

        bundle = new Bundle();
        bundle.putString(GlobalConstant.TAG_YEAR, "" + GlobalConstant.getPreviousYear());
        bundle.putString(GlobalConstant.TAG_ANALYSIS_TYPE, "AREA PLANTED");
        bundle.putString(GlobalConstant.TAG_STATE_NAME, "");
        bundle.putString(GlobalConstant.TAG_CROP_NAME, "");
        bundle.putString(GlobalConstant.TAG_isShowLoadingDialog, GlobalConstant.TAG_NO);
        Fragment fragmentAreaPlanned = new CommonCropsCardListFragment();
        fragmentAreaPlanned.setArguments(bundle);
        adapter.addFragment(fragmentAreaPlanned, "AREA PLANTED");


        /*
        bundle = new Bundle();
        bundle.putString(GlobalConstant.TAG_YEAR, ""+GlobalConstant.getPreviousYear());
        bundle.putString(GlobalConstant.TAG_ANALYSIS_TYPE, "AREA HARVESTED");
        bundle.putString(GlobalConstant.TAG_STATE_NAME, "");
        bundle.putString(GlobalConstant.TAG_CROP_NAME, "");
        bundle.putString(GlobalConstant.TAG_isShowLoadingDialog, GlobalConstant.TAG_NO);
        Fragment fragmentAreaHarvested = new CommonCropsCardListFragment();
        fragmentAreaHarvested.setArguments(bundle);
        adapter.addFragment(fragmentAreaHarvested, "AREA HARVESTED");
        */

        viewPager.setAdapter(adapter);
    }

    public void compareActivity() {
        Intent intent = new Intent(MainActivity.this, CompareActivity.class);
        intent.putExtra(GlobalConstant.TAG_state_name, getResources().getStringArray(R.array.state_name)[0]);
        intent.putExtra(GlobalConstant.TAG_FIRST_COMPARE_ITEM, getResources().getStringArray(R.array.crop_name)[0]);
        intent.putExtra(GlobalConstant.TAG_SECOND_COMPARE_ITEM, getResources().getStringArray(R.array.crop_name)[0]);
        intent.putExtra(GlobalConstant.TAG_statisticcat_desc, getResources().getStringArray(R.array.statistic_category)[0]);
        intent.putExtra(GlobalConstant.TAG_RELOAD, GlobalConstant.TAG_NO);
        startActivity(intent);
    }

    public void singleProductionActivity(String stateName, String cropName, String year, String statisticCategory) {
        Intent intent = new Intent(MainActivity.this, SingleItemAnalysisActivity.class);
        intent.putExtra(GlobalConstant.TAG_state_name, stateName);
        intent.putExtra(GlobalConstant.TAG_commodity_desc, cropName);
        intent.putExtra(GlobalConstant.TAG_year, year);
        intent.putExtra(GlobalConstant.TAG_statisticcat_desc, statisticCategory);
        startActivity(intent);
    }

    public void expenseAnalysisActivity() {
        Intent intent = new Intent(MainActivity.this, ExpensesActivity.class);
        intent.putExtra(GlobalConstant.TAG_state_name, selectedText);
        intent.putExtra(GlobalConstant.TAG_EXPENSES_TYPE, expenseType);
        startActivity(intent);
    }

    public void stateBasedAnalysis() {
        selectOption = -1;
        MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                .title("Select State")
                .items(getResources().getStringArray(R.array.state_name))
                .autoDismiss(false)
                .forceStacking(false)
                .positiveText("Select")
                .negativeText("Close")
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text != null) {
                            selectOption = which;
                            selectedText = text.toString();
                        }
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                        if (selectOption != -1) {
                            Intent in = new Intent(MainActivity.this, CommonAnalysisListActivity.class);
                            in.putExtra(GlobalConstant.TAG_commodity_desc, "");
                            in.putExtra(GlobalConstant.TAG_state_name, selectedText);
                            in.putExtra(GlobalConstant.TAG_year, "" + GlobalConstant.getPreviousYear());
                            startActivity(in);
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    public void cropBasedAnalysis() {
        selectOption = -1;
        MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                .title("Select Crop")
                .items(getResources().getStringArray(R.array.crop_name))
                .autoDismiss(false)
                .forceStacking(false)
                .positiveText("Select")
                .negativeText("Close")
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text != null) {
                            selectOption = which;
                            selectedText = text.toString();
                        }
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                        if (selectOption != -1) {
                            Intent in = new Intent(MainActivity.this, CommonAnalysisListActivity.class);
                            in.putExtra(GlobalConstant.TAG_commodity_desc, selectedText);
                            in.putExtra(GlobalConstant.TAG_state_name, "");
                            in.putExtra(GlobalConstant.TAG_year, "" + GlobalConstant.getPreviousYear());
                            startActivity(in);
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    public void productionStateAnalysis() {
        selectOption = -1;
        MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                .title("Select State")
                .items(getResources().getStringArray(R.array.state_name))
                .autoDismiss(false)
                .forceStacking(false)
                .positiveText("Select")
                .negativeText("Close")
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text != null) {
                            selectOption = which;
                            selectedText = text.toString();
                        }
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                        if (selectOption != -1) {
                            singleProductionActivity(selectedText, "", "" + GlobalConstant.getPreviousYear(), GlobalConstant.TAG_AT_PRODUCTION);
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    public void productionCropAnalysis() {
        selectOption = -1;
        MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                .title("Select Crop")
                .items(getResources().getStringArray(R.array.crop_name))
                .autoDismiss(false)
                .forceStacking(false)
                .positiveText("Select")
                .negativeText("Close")
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text != null) {
                            selectOption = which;
                            selectedText = text.toString();
                        }
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                        if (selectOption != -1) {
                            singleProductionActivity("", selectedText, "" + GlobalConstant.getPreviousYear(), GlobalConstant.TAG_AT_PRODUCTION);
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    public void chooseStateExpenseAnalysis() {
        selectOption = -1;
        MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                .title("Select State")
                .items(getResources().getStringArray(R.array.state_name))
                .autoDismiss(false)
                .forceStacking(false)
                .positiveText("Select")
                .negativeText("Close")
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        if (text != null) {
                            selectOption = which;
                            selectedText = text.toString();
                        }
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                        if (selectOption != -1) {
                            expenseAnalysisActivity();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setText("Home");
        tabLayout.getTabAt(1).setText("Yield");
        tabLayout.getTabAt(2).setText("PRICE RECEIVED");
        tabLayout.getTabAt(3).setText("Area PLANTED");
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
