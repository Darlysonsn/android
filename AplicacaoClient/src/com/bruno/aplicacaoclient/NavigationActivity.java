package com.bruno.aplicacaoclient;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationActivity extends ActionBarActivity implements ListView.OnItemClickListener{
	
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String Titulos[] = {"Minha Localização Atual","Meus Trajetos"};
    private Toolbar toolbar;                              
    private DrawerLayout Drawer;                                  
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;                  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
 
    toolbar = (Toolbar) findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    
    mDrawerList = (ListView) findViewById(R.id.left_drawer);

    Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);     
    
    View list_header = getLayoutInflater().inflate(R.layout.imagens_item, null);
    
    navigationDrawerAdapter = new ArrayAdapter<String>(NavigationActivity.this, android.R.layout.simple_expandable_list_item_1, Titulos);
    mDrawerList.setAdapter(navigationDrawerAdapter);
    mDrawerList.setOnItemClickListener(this);
    mDrawerList.addHeaderView(list_header);

	mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
    	  
    
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            invalidateOptionsMenu(); 
        }
        
        

}; 
    Drawer.setDrawerListener(mDrawerToggle); 
}
    
	@Override
	public void onItemClick(AdapterView parent, View view, int position, long id) {
		switch (position){
		
		case 1:
            startActivity(new Intent(this, MinhaLocalizacao.class));
	         Drawer.closeDrawer(mDrawerList);
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
                      
		break;
		case 2:
            startActivity(new Intent(this, ListaViewLocalizacao.class));
	         Drawer.closeDrawer(mDrawerList);
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
                      
		break;
		
			
		}

	}

	  @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        mDrawerToggle.syncState();
	    }
    
}