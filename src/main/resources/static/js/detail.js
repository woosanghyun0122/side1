    function toggleWishlist() {

        const icon = document.getElementById('heart').querySelector('.fa-heart');
        icon.classList.toggle('fas'); // 채워진 하트로 변경
        icon.classList.toggle('far'); // 빈 하트로 변경
        icon.classList.toggle('filled'); // 색상을 위한 클래스 추가
    }