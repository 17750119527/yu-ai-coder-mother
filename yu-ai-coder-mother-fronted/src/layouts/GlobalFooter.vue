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
        <span>© 2026 YU-AI-Coder. 保留所有权利。</span>
      </div>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
// GlobalFooter 组件
</script>

<style scoped>
.global-footer {
  background-color: #f0f2f5;
  border-top: 1px solid #e8e8e8;
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
  color: #666;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s;
}

.footer-link:hover {
  color: #1890ff;
}

.footer-copyright {
  color: #999;
  font-size: 12px;
  text-align: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-content {
    padding: 0 16px;
  }
  
  .footer-links {
    flex-wrap: wrap;
    justify-content: center;
    gap: 16px;
  }
  
  .footer-link {
    font-size: 12px;
  }
  
  .footer-copyright {
    font-size: 10px;
  }
}
</style>