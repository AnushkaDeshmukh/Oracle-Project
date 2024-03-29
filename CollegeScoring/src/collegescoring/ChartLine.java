/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package collegescoring;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * A chart in which lines connect a series of data points. Useful for viewing
 * data trends over time.
 *
 * @see javafx.scene.chart.LineChart
 * @see javafx.scene.chart.Chart
 * @see javafx.scene.chart.Axis
 * @see javafx.scene.chart.NumberAxis
 * @related charts/area/AreaChart
 * @related charts/scatter/ScatterChart
 */
public class ChartLine extends Application {

    private void init(Stage primaryStage) {
        CollegeScoring collegeScoring = new CollegeScoring();
        collegeScoring.calc();
        Group root = new Group();
        primaryStage.setTitle("Results from Inference Test");
        primaryStage.setScene(new Scene(root));
        NumberAxis xAxis = new NumberAxis("SAT weightage", 0, 1.1, 0.1);
        NumberAxis yAxis = new NumberAxis("P-value", 0, 0.15, 0.01);
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList(
                new LineChart.Series<Double, Double>("Series 1", FXCollections.observableArrayList()));
        lineChartData.get(0).getData().add(new XYChart.Data<Double, Double>(1.0, collegeScoring.pSATonly));
        for (CollegeScoring.Weights w : CollegeScoring.Weights.values()) {
            lineChartData.get(0).getData().add(new XYChart.Data<Double, Double>(w.sPercent, collegeScoring.p[w.ordinal()]));
        }
        lineChartData.get(0).getData().add(new XYChart.Data<Double, Double>(0.0, collegeScoring.pGPAonly));
        LineChart chart = new LineChart(xAxis, yAxis, lineChartData);
        root.getChildren().add(chart);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
