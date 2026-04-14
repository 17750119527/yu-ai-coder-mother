<template>
  <a-layout-footer class="global-footer">
    <div class="footer-content">
      <div class="footer-links">
        <a href="#" class="footer-link">关于我们</a>
        <a href="#" class="footer-link">使用条款</a>
        <a href="#" class="footer-link">隐私政策</a>
        <a href="#" class="footer-link">联系我们</a>
      </div>
      <div class="footer-copyright">
        <span>&copy; 2026 YU-AI-Coder. 保留所有权利。</span>
      </div>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
</script>

<style scoped>
.global-footer {
  background-color: #0B1120;
  border-top: 1px solid #1E293B;
  padding: 24px 0;
  margin-top: auto;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.footer-links {
  display: flex;
  gap: 24px;
  margin-bottom: 16px;
}

.footer-link {
  color: #64748B;
  text-decoration: none;
  font-size: 13px;
  transition: color 0.2s;
  cursor: pointer;
}

.footer-link:hover {
  color: #22C55E;
}

.footer-copyright {
  color: #475569;
  font-size: 12px;
}

@media (max-width: 768px) {
  .footer-content {
    padding: 0 16px;
  }

  .footer-links {
    flex-wrap: wrap;
    justify-content: center;
    gap: 16px;
  }
}
</style>
