package codev19.utils;

import codev19.model.Analyze;

public class CovidCounter {
    private Analyze PrevData;   // 이전 데이터
    private Analyze NewData;    // 새로운 데이터
    private Analyze AnalyzeData;    // 분석된 데이터

    // 전체 개수 카운트
    private int TotalCount;
    private int CityCount;
    private int StateCount;
    private int StreetCount;
    private int ClinicCount;

    // 양성 환자 개수 카운트
    private int VirusTotal;
    private int VirusState;
    private int VirusCity;
    private int VirusStreet;
    private int VirusClinic;

    // Analyze

    // 생성자
    public CovidCounter(){
        TotalCount = 0;
        StateCount = 0;
        CityCount = 0;
        StreetCount = 0;
        ClinicCount = 0;

        VirusTotal = 0;
        VirusState = 0;
        VirusCity = 0;
        VirusStreet = 0;
        VirusClinic = 0;
    }

    public boolean inputData(String state, String city, String street, String clinic, String people, int result){
        // 새로운 데이터로 테이블 ROW 생성
        NewData = new Analyze(state, city, street, clinic, people, 1, result);

        // 첫번째 데이터일 경우
        if(PrevData == null){
            PrevData = NewData;
            return false;
        }

        // 검사 결과가 양성일 경우
        if(result == 1){
            VirusState++;
            VirusCity++;
            VirusStreet++;
            VirusClinic++;
            VirusTotal++;
        }

        // Clinic가 이전 데이터랑 다른 경우
        if(!PrevData.getClinic().equals(NewData.getClinic())){
            AnalyzeData = new Analyze(PrevData.getState(), PrevData.getCity(), PrevData.getStreet(), PrevData.getClinic(), "", ClinicCount, VirusClinic);
            ClinicCount = 0;
            VirusClinic = 0;
            PrevData.setClinic(clinic);
            return true;
        }
        // Street가 이전 데이터랑 다른 경우
        if(!PrevData.getStreet().equals(NewData.getStreet())){
            AnalyzeData = new Analyze(PrevData.getState(), PrevData.getCity(), PrevData.getStreet(), "", "", StreetCount, VirusStreet);
            StreetCount = 0;
            VirusStreet = 0;
            PrevData.setStreet(street);
            return  true;
        }
        // City가 이전 데이터랑 다른 경우
        if(!PrevData.getCity().equals(NewData.getCity())){
            AnalyzeData = new Analyze(PrevData.getState(), PrevData.getCity(), "", "", "", CityCount, VirusCity);
            CityCount = 0;
            VirusCity = 0;
            PrevData.setCity(city);
            return  true;
        }
        // State가 이전 데이터랑 다른 경우
        if(!PrevData.getState().equals(NewData.getState())){
            AnalyzeData = new Analyze(PrevData.getState(), "", "", "", "", StateCount, VirusState);
            StateCount = 0;
            VirusState = 0;
            PrevData.setState(state);
            return true;
        }

        // 전체 개수 증가
        TotalCount++;
        StateCount++;
        CityCount++;
        StreetCount++;
        ClinicCount++;

        // 과거 데이터 저장
        PrevData = NewData;

        return false;
    }

    public Analyze outputData(){
        return NewData;
    }
    public Analyze outputAnalyze(){
        return AnalyzeData;
    }

    public Analyze totalAnalyze(){
        return new Analyze("전국", "", "", "", "", TotalCount, VirusTotal);
    }




}
