# 📱 Tank Monitoring Android App

## 개요
이 프로젝트는 **실시간 산업용 탱크 모니터링 시스템**의 안드로이드 클라이언트입니다.

- JWT 기반 로그인
- 실시간 탱크 상태 조회 (5초 간격 자동 갱신)
- 과거 이력 데이터 페이지네이션 조회 (그룹별 필터링)

백엔드 API는 Django REST Framework를 사용하며, Retrofit으로 연동됩니다.

---

## 📂 프로젝트 구조
com.donghyun.tankmonitoringapp
├── LoginActivity.kt
├── DashboardFragment.kt
├── HistoryFragment.kt
├── DashboardContainerActivity.kt
├── adapter/
│ ├── TankAdapter.kt
│ └── TankHistoryAdapter.kt
├── model/
│ └── TankData.kt
├── RetrofitClient.kt
├── TankApiService.kt


---

## 🔑 주요 기능

### 1. JWT 로그인
- `LoginActivity`
  - 사용자 입력(아이디/비밀번호)으로 JWT Access Token 발급
  - `SharedPreferences`에 토큰 저장
  - 로그인 성공 시 `DashboardContainerActivity`로 이동
  - 실패 시 오류 메시지 출력

**요청**
POST /api/token/
Content-Type: application/json

{
"username": "사용자명",
"password": "비밀번호"
}

---

### 2. 실시간 대시보드
- `DashboardFragment`
  - 앱 진입 시 토큰을 읽어 `Authorization` 헤더 생성
  - `5초`마다 최신 데이터 자동 조회
  - RecyclerView에 실시간 표시

**동작 흐름**
- Handler + Runnable로 주기적 갱신
- Retrofit 비동기 호출
- RecyclerView 어댑터 갱신

**요청**
GET /latest-tank-data/
Authorization: Bearer <JWT_TOKEN> 


---

### 3. 과거 데이터 조회
- `HistoryFragment`
  - Spinner로 그룹 선택 (전체/1~6번)
  - 페이지 네비게이션 (`Prev`, `Next`)
  - 페이지 정보 표시
  - 20개 단위 데이터 조회

**요청**
GET /history-data/
Authorization: Bearer <JWT_TOKEN>
Query:
tank_id=<그룹ID>
page=<현재페이지>
limit=20 


---

## 🛠️ 사용 라이브러리
- Retrofit2: REST API 통신
- Gson: JSON 처리
- RecyclerView: 리스트 출력
- Handler: 주기적 작업
- SharedPreferences: 로컬 토큰 저장

---

## 💡 실행 순서
1. 로그인 시도 (`LoginActivity`)
2. 로그인 성공 시 대시보드 화면 진입
3. `DashboardFragment`에서 실시간 데이터 표시
4. `HistoryFragment`에서 과거 데이터 페이지별 조회
