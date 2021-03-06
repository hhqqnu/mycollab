/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.user.domain;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ucd")
public class BillingPlanExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public BillingPlanExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    @SuppressWarnings("ucd")
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBillingtypeIsNull() {
            addCriterion("billingType is null");
            return (Criteria) this;
        }

        public Criteria andBillingtypeIsNotNull() {
            addCriterion("billingType is not null");
            return (Criteria) this;
        }

        public Criteria andBillingtypeEqualTo(String value) {
            addCriterion("billingType =", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotEqualTo(String value) {
            addCriterion("billingType <>", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeGreaterThan(String value) {
            addCriterion("billingType >", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeGreaterThanOrEqualTo(String value) {
            addCriterion("billingType >=", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeLessThan(String value) {
            addCriterion("billingType <", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeLessThanOrEqualTo(String value) {
            addCriterion("billingType <=", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeLike(String value) {
            addCriterion("billingType like", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotLike(String value) {
            addCriterion("billingType not like", value, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeIn(List<String> values) {
            addCriterion("billingType in", values, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotIn(List<String> values) {
            addCriterion("billingType not in", values, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeBetween(String value1, String value2) {
            addCriterion("billingType between", value1, value2, "billingtype");
            return (Criteria) this;
        }

        public Criteria andBillingtypeNotBetween(String value1, String value2) {
            addCriterion("billingType not between", value1, value2, "billingtype");
            return (Criteria) this;
        }

        public Criteria andNumusersIsNull() {
            addCriterion("numUsers is null");
            return (Criteria) this;
        }

        public Criteria andNumusersIsNotNull() {
            addCriterion("numUsers is not null");
            return (Criteria) this;
        }

        public Criteria andNumusersEqualTo(Integer value) {
            addCriterion("numUsers =", value, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersNotEqualTo(Integer value) {
            addCriterion("numUsers <>", value, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersGreaterThan(Integer value) {
            addCriterion("numUsers >", value, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersGreaterThanOrEqualTo(Integer value) {
            addCriterion("numUsers >=", value, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersLessThan(Integer value) {
            addCriterion("numUsers <", value, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersLessThanOrEqualTo(Integer value) {
            addCriterion("numUsers <=", value, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersIn(List<Integer> values) {
            addCriterion("numUsers in", values, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersNotIn(List<Integer> values) {
            addCriterion("numUsers not in", values, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersBetween(Integer value1, Integer value2) {
            addCriterion("numUsers between", value1, value2, "numusers");
            return (Criteria) this;
        }

        public Criteria andNumusersNotBetween(Integer value1, Integer value2) {
            addCriterion("numUsers not between", value1, value2, "numusers");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNull() {
            addCriterion("volume is null");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNotNull() {
            addCriterion("volume is not null");
            return (Criteria) this;
        }

        public Criteria andVolumeEqualTo(Long value) {
            addCriterion("volume =", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotEqualTo(Long value) {
            addCriterion("volume <>", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThan(Long value) {
            addCriterion("volume >", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThanOrEqualTo(Long value) {
            addCriterion("volume >=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThan(Long value) {
            addCriterion("volume <", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThanOrEqualTo(Long value) {
            addCriterion("volume <=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeIn(List<Long> values) {
            addCriterion("volume in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotIn(List<Long> values) {
            addCriterion("volume not in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeBetween(Long value1, Long value2) {
            addCriterion("volume between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotBetween(Long value1, Long value2) {
            addCriterion("volume not between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andNumprojectsIsNull() {
            addCriterion("numProjects is null");
            return (Criteria) this;
        }

        public Criteria andNumprojectsIsNotNull() {
            addCriterion("numProjects is not null");
            return (Criteria) this;
        }

        public Criteria andNumprojectsEqualTo(Integer value) {
            addCriterion("numProjects =", value, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsNotEqualTo(Integer value) {
            addCriterion("numProjects <>", value, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsGreaterThan(Integer value) {
            addCriterion("numProjects >", value, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsGreaterThanOrEqualTo(Integer value) {
            addCriterion("numProjects >=", value, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsLessThan(Integer value) {
            addCriterion("numProjects <", value, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsLessThanOrEqualTo(Integer value) {
            addCriterion("numProjects <=", value, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsIn(List<Integer> values) {
            addCriterion("numProjects in", values, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsNotIn(List<Integer> values) {
            addCriterion("numProjects not in", values, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsBetween(Integer value1, Integer value2) {
            addCriterion("numProjects between", value1, value2, "numprojects");
            return (Criteria) this;
        }

        public Criteria andNumprojectsNotBetween(Integer value1, Integer value2) {
            addCriterion("numProjects not between", value1, value2, "numprojects");
            return (Criteria) this;
        }

        public Criteria andPricingIsNull() {
            addCriterion("pricing is null");
            return (Criteria) this;
        }

        public Criteria andPricingIsNotNull() {
            addCriterion("pricing is not null");
            return (Criteria) this;
        }

        public Criteria andPricingEqualTo(Double value) {
            addCriterion("pricing =", value, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingNotEqualTo(Double value) {
            addCriterion("pricing <>", value, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingGreaterThan(Double value) {
            addCriterion("pricing >", value, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingGreaterThanOrEqualTo(Double value) {
            addCriterion("pricing >=", value, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingLessThan(Double value) {
            addCriterion("pricing <", value, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingLessThanOrEqualTo(Double value) {
            addCriterion("pricing <=", value, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingIn(List<Double> values) {
            addCriterion("pricing in", values, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingNotIn(List<Double> values) {
            addCriterion("pricing not in", values, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingBetween(Double value1, Double value2) {
            addCriterion("pricing between", value1, value2, "pricing");
            return (Criteria) this;
        }

        public Criteria andPricingNotBetween(Double value1, Double value2) {
            addCriterion("pricing not between", value1, value2, "pricing");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andHasbugenableIsNull() {
            addCriterion("hasBugEnable is null");
            return (Criteria) this;
        }

        public Criteria andHasbugenableIsNotNull() {
            addCriterion("hasBugEnable is not null");
            return (Criteria) this;
        }

        public Criteria andHasbugenableEqualTo(Boolean value) {
            addCriterion("hasBugEnable =", value, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableNotEqualTo(Boolean value) {
            addCriterion("hasBugEnable <>", value, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableGreaterThan(Boolean value) {
            addCriterion("hasBugEnable >", value, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("hasBugEnable >=", value, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableLessThan(Boolean value) {
            addCriterion("hasBugEnable <", value, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableLessThanOrEqualTo(Boolean value) {
            addCriterion("hasBugEnable <=", value, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableIn(List<Boolean> values) {
            addCriterion("hasBugEnable in", values, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableNotIn(List<Boolean> values) {
            addCriterion("hasBugEnable not in", values, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableBetween(Boolean value1, Boolean value2) {
            addCriterion("hasBugEnable between", value1, value2, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasbugenableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("hasBugEnable not between", value1, value2, "hasbugenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableIsNull() {
            addCriterion("hasStandupMeetingEnable is null");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableIsNotNull() {
            addCriterion("hasStandupMeetingEnable is not null");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableEqualTo(Boolean value) {
            addCriterion("hasStandupMeetingEnable =", value, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableNotEqualTo(Boolean value) {
            addCriterion("hasStandupMeetingEnable <>", value, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableGreaterThan(Boolean value) {
            addCriterion("hasStandupMeetingEnable >", value, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("hasStandupMeetingEnable >=", value, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableLessThan(Boolean value) {
            addCriterion("hasStandupMeetingEnable <", value, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableLessThanOrEqualTo(Boolean value) {
            addCriterion("hasStandupMeetingEnable <=", value, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableIn(List<Boolean> values) {
            addCriterion("hasStandupMeetingEnable in", values, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableNotIn(List<Boolean> values) {
            addCriterion("hasStandupMeetingEnable not in", values, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableBetween(Boolean value1, Boolean value2) {
            addCriterion("hasStandupMeetingEnable between", value1, value2, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHasstandupmeetingenableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("hasStandupMeetingEnable not between", value1, value2, "hasstandupmeetingenable");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingIsNull() {
            addCriterion("hasTimeTracking is null");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingIsNotNull() {
            addCriterion("hasTimeTracking is not null");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingEqualTo(Boolean value) {
            addCriterion("hasTimeTracking =", value, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingNotEqualTo(Boolean value) {
            addCriterion("hasTimeTracking <>", value, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingGreaterThan(Boolean value) {
            addCriterion("hasTimeTracking >", value, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingGreaterThanOrEqualTo(Boolean value) {
            addCriterion("hasTimeTracking >=", value, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingLessThan(Boolean value) {
            addCriterion("hasTimeTracking <", value, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingLessThanOrEqualTo(Boolean value) {
            addCriterion("hasTimeTracking <=", value, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingIn(List<Boolean> values) {
            addCriterion("hasTimeTracking in", values, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingNotIn(List<Boolean> values) {
            addCriterion("hasTimeTracking not in", values, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingBetween(Boolean value1, Boolean value2) {
            addCriterion("hasTimeTracking between", value1, value2, "hastimetracking");
            return (Criteria) this;
        }

        public Criteria andHastimetrackingNotBetween(Boolean value1, Boolean value2) {
            addCriterion("hasTimeTracking not between", value1, value2, "hastimetracking");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table s_billing_plan
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 24 09:27:16 ICT 2014
     */
    @SuppressWarnings("ucd")
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table s_billing_plan
     *
     * @mbggenerated Wed Sep 24 09:27:16 ICT 2014
     */
    @SuppressWarnings("ucd")
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}