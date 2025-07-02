INSERT INTO books (id, author_id, author_name, title, point, category, summary, content, image_url, audio_url, today_count, total_count, created_at)
VALUES
(CAST('7f29d760-50c9-4e7c-810f-91b6f8b1de01' AS UUID),
 CAST('a1b2c3d4-e5f6-7890-abcd-ef1234567890' AS UUID),
 '김작가',
 '스프링 부트 완전정복',
 12000,
 '개발',
 '스프링 부트를 배우는 최고의 책',
 '이 책은 스프링 부트의 모든 것을 다룹니다...',
 'https://example.com/book1.jpg',
 'https://example.com/audio1.mp3',
 50,
 1250,
 CURRENT_TIMESTAMP),

(CAST('5b2a3891-d7c3-4e90-aaf4-1a4b117a8ea2' AS UUID),
 CAST('b2c3d4e5-f6f7-8901-bcde-f23456789012' AS UUID), -- 👈 g7 → f7 수정
 '박개발',
 'React 마스터하기',
 5000,
 '개발',
 'React 전문가가 되는 길',
 'React의 기초부터 고급까지...',
 'https://example.com/book2.jpg',
 'https://example.com/audio2.mp3',
 30,
 890,
 CURRENT_TIMESTAMP),

(CAST('c85b0aa7-8e2f-47db-a58c-9a7a6e6b4e9d' AS UUID),
 CAST('c3d4e5f6-a7b8-9012-cdef-345678901234' AS UUID), -- 👈 g7h8 → a7b8 수정
 '이디자인',
 'UI/UX 디자인 가이드',
 18000,
 '디자인',
 '사용자 경험을 위한 디자인',
 '좋은 디자인의 원칙들...',
 'https://example.com/book3.jpg',
 'https://example.com/audio3.mp3',
 80,
 2100,
 CURRENT_TIMESTAMP);




-- 테스트 구매 데이터 삽입
INSERT INTO purchases (id,user_id, book_id, created_at) VALUES
(UUID(),CAST('11111111-1111-1111-1111-111111111111' AS UUID),
CAST('7f29d760-50c9-4e7c-810f-91b6f8b1de01' AS UUID), '2024-06-01 10:30:00'),
(UUID(),CAST('11111111-1111-1111-1111-111111111111' AS UUID),
CAST('7f29d760-50c9-4e7c-810f-91b6f8b1de01' AS UUID), '2024-06-15 16:45:00'),
(UUID(),CAST('22222222-2222-2222-2222-222222222222' AS UUID),
CAST('7f29d760-50c9-4e7c-810f-91b6f8b1de01' AS UUID), '2024-06-20 11:20:00');
