package com.redsamurai.view.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.controller.binding.TaskFlowBindingAttributes;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ItemEvent;

import org.apache.myfaces.trinidad.event.DisclosureEvent;


public class Main {
    private List<TaskFlowBindingAttributes> mTaskFlowBindingAttrs = new ArrayList<TaskFlowBindingAttributes>(5);
    private RichPanelTabbed panelTabbed;
    private String tabSelected;
    private RichPanelGroupLayout rootTabComp;

    public Main() {
    }
    
    public List<TaskFlowBindingAttributes> getTaskFlowList() {
        return mTaskFlowBindingAttrs;
    }
    
    public int getTabsSize() {
        return mTaskFlowBindingAttrs.size();
    }

    public void handleCloseTabItem(ItemEvent itemEvent) {
        if (itemEvent.getType().equals(ItemEvent.Type.remove)) {
            Object item = itemEvent.getSource();
            if (item instanceof RichShowDetailItem) {
                RichShowDetailItem tabItem = (RichShowDetailItem)item;
                String tfName = (String)tabItem.getAttributes().get("tfName");
                
                int discloseIndex = 0;
                for (int i = 0; i < mTaskFlowBindingAttrs.size(); i++) {
                    TaskFlowBindingAttributes tf = mTaskFlowBindingAttrs.get(i);
                    if (tf.getId().equals(tfName)) {
                        mTaskFlowBindingAttrs.remove(tf);
                        discloseIndex = i;
                        break;
                    }
                }
                
                if (mTaskFlowBindingAttrs.size() > 0) {
                    if (discloseIndex == mTaskFlowBindingAttrs.size() && discloseIndex > 0) {
                        discloseIndex -= 1;
                    }
                    this.makeSelected(discloseIndex);
                } else {
                    this.setTabSelected(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRootTabComp());
                }
            }
        }
    }

    public void regionOneLaunch(ActionEvent actionEvent) {
        int index = this.tfExist("Locations");
        if (index < 0) {
            TaskFlowBindingAttributes tfAttr = new TaskFlowBindingAttributes();
            tfAttr.setId("Locations");
            tfAttr.setTaskFlowId(new TaskFlowId("/WEB-INF/flows/locs-flows.xml", "locs-flows"));
            mTaskFlowBindingAttrs.add(tfAttr);
            if (this.getPanelTabbed().getChildCount() > 0) {    
                this.setTabSelected(tfAttr.getId());
            }
        } else {
            this.makeSelected(index);
        }
    }

    public void regionTwoLaunch(ActionEvent actionEvent) {
        int index = this.tfExist("Departments");
        if (index < 0) {
            TaskFlowBindingAttributes tfAttr = new TaskFlowBindingAttributes();
            tfAttr.setId("Departments");
            tfAttr.setTaskFlowId(new TaskFlowId("/WEB-INF/flows/deps-flow.xml", "deps-flow"));
            mTaskFlowBindingAttrs.add(tfAttr);
            if (this.getPanelTabbed().getChildCount() > 0) {  
                this.setTabSelected(tfAttr.getId());
            }
        } else {
            this.makeSelected(index);
        }
    }

    public void regionThreeLaunch(ActionEvent actionEvent) {
        int index = this.tfExist("Employees");
        if (index < 0) {
            TaskFlowBindingAttributes tfAttr = new TaskFlowBindingAttributes();
            tfAttr.setId("Employees");
            tfAttr.setTaskFlowId(new TaskFlowId("/WEB-INF/flows/empls-flow.xml", "empls-flow"));  
            mTaskFlowBindingAttrs.add(tfAttr);
            if (this.getPanelTabbed().getChildCount() > 0) {
                this.setTabSelected(tfAttr.getId());
            }
        } else {
            this.makeSelected(index);
        }
    }
    
    public void regionFourLaunch(ActionEvent actionEvent) {
        int index = this.tfExist("Jobs");
        if (index < 0) {
            TaskFlowBindingAttributes tfAttr = new TaskFlowBindingAttributes();
            tfAttr.setId("Jobs");
            tfAttr.setTaskFlowId(new TaskFlowId("/WEB-INF/flows/jobs-flow.xml", "jobs-flow"));  
            mTaskFlowBindingAttrs.add(tfAttr);
            if (this.getPanelTabbed().getChildCount() > 0) {
                this.setTabSelected(tfAttr.getId());
            }
        } else {
            this.makeSelected(index);
        }
    }
    
    public void regionReportLaunch(ActionEvent actionEvent) {
        int index = this.tfExist("Report");
        if (index < 0) {
            TaskFlowBindingAttributes tfAttr = new TaskFlowBindingAttributes();
            tfAttr.setId("Report");
            tfAttr.setTaskFlowId(new TaskFlowId("/WEB-INF/flows/report-flow.xml", "report-flow"));  
            mTaskFlowBindingAttrs.add(tfAttr);
            if (this.getPanelTabbed().getChildCount() > 0) {
                this.setTabSelected(tfAttr.getId());
            }
        } else {
            this.makeSelected(index);
        }
    }
    
    private int tfExist(String tfName) {
        for (int i = 0; i < mTaskFlowBindingAttrs.size(); i++) {
            TaskFlowBindingAttributes tf = mTaskFlowBindingAttrs.get(i);
            if (tf.getId().equals(tfName)) {
                return i;
            }
        }     
        return -1;
    }
    
    private void makeSelected(int index) {
        List<UIComponent> tabs = this.getPanelTabbed().getChildren();
        for (int i = 0; i < tabs.size(); i++) {
            RichShowDetailItem tab = (RichShowDetailItem)tabs.get(i);
            tab.setDisclosed(false);
        }           
        RichShowDetailItem tab = (RichShowDetailItem)tabs.get(index);
        tab.setDisclosed(true);
        
        String tfNameText = (String)tab.getAttributes().get("tfName");
        this.setTabSelected(tfNameText);
    }
    
    public Map getDisclosed() {
        return new HashMap() {
            @Override
            public Object get(Object tfName) {
                List<UIComponent> tabs = getPanelTabbed().getChildren();
                for (int i = 0; i < tabs.size(); i++) {
                    RichShowDetailItem tab = (RichShowDetailItem)tabs.get(i);
                    tab.setDisclosed(false);
                    
                    String tfNameText = (String)tab.getAttributes().get("tfName");
                    if (tfNameText.equals(getTabSelected())) {
                        tab.setDisclosed(true);
                    }
                }  
                               
                return false;
            }
        };
    } 
    
    public void disclosureListener(DisclosureEvent disclosureEvent) {
        RichShowDetailItem tab = (RichShowDetailItem)disclosureEvent.getComponent();
        String tfNameText = (String)tab.getAttributes().get("tfName");
        
        this.setTabSelected(tfNameText);
    }

    public void setPanelTabbed(RichPanelTabbed panelTabbed) {
        this.panelTabbed = panelTabbed;
    }

    public RichPanelTabbed getPanelTabbed() {
        return panelTabbed;
    }

    public void setTabSelected(String tabSelected) {
        this.tabSelected = tabSelected;
    }

    public String getTabSelected() {
        return tabSelected;
    }

    public void setRootTabComp(RichPanelGroupLayout rootTabComp) {
        this.rootTabComp = rootTabComp;
    }

    public RichPanelGroupLayout getRootTabComp() {
        return rootTabComp;
    }
}
