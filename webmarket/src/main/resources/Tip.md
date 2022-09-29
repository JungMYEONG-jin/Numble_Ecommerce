ManyToOne이 주인임 여기에 fetch LAZY로 주면 proxy로 객체를 생성

## Lazy Loading에서 발생하는 에러

그래서 오류 메시지를 다시 읽어보자면

```shell
could not initialize proxy - no Session
```

여기서 Session은 영속성 컨텍스트를 관리하는 엔티티 매니저라고 생각하면 된다.
즉, 영속 상태인 Proxy 객체에 실제 데이터를 불러오려고 초기화를 시도하지만 Session이 close되어서 준영속 상태가 되어 값을 가져올 수가 없어 발생한 오류임을 알 수 있다.
다시말해서, 지연 로딩을 하려면 해당 객체는 무조건 영속성 컨텍스트에서 관리해야 한다.

해결법은 @Transactional을 줘서 같은 세션에 유지시키자



UserDetails 란?

Spring Security에서 사용자의 정보를 담는 인터페이스이다.

Spring Security에서 사용자의 정보를 불러오기 위해서 구현해야 하는 인터페이스로 기본 오버라이드 메서드들은 아래와 같다.


메소드	            리턴 타입	                                설명	기본값
getAuthorities()	Collection<? extends GrantedAuthority>	계정의 권한 목록을 리턴	 
getPassword()	    String	계정의 비밀번호를 리턴	 
getUsername()	    String	계정의 고유한 값을 리턴               ( ex : DB PK값, 중복이 없는 이메일 값 )	 
isAccountNonExpired()	boolean	계정의 만료 여부 리턴	        true ( 만료 안됨 )
isAccountNonLocked()	boolean	계정의 잠김 여부 리턴	        true ( 잠기지 않음 )
isCredentialsNonExpired()	boolean	비밀번호 만료 여부 리턴	    true ( 만료 안됨 )
isEnabled()	        boolean	계정의 활성화 여부 리턴	            true ( 활성화 됨 )